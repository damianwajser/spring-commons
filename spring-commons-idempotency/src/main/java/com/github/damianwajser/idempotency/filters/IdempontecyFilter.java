package com.github.damianwajser.idempotency.filters;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.exceptions.impl.badrequest.ConflictException;
import com.github.damianwajser.idempotency.configuration.IdempotencyEndpoints;
import com.github.damianwajser.idempotency.configuration.IdempotencyProperties;
import com.github.damianwajser.idempotency.exception.ArgumentNotFoundException;
import com.github.damianwajser.idempotency.exception.RedisException;
import com.github.damianwajser.idempotency.model.StoredResponse;
import com.github.damianwajser.idempotency.utils.HeadersUtil;
import com.github.damianwajser.idempotency.utils.JsonUtils;
import com.github.damianwajser.idempotency.writers.HttpServletResponseCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class IdempontecyFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdempontecyFilter.class);
	private static final String ERROR_REDIS = "Error Redis retrive information to key: {}";

	private final IdempotencyProperties idempotencyProperties;
	private final RedisTemplate<String, Object> redisTemplate;
	private final IdempotencyEndpoints idempotencyEndpoints;

	public IdempontecyFilter(RedisTemplate<String, Object> redisTemplate, IdempotencyEndpoints idempotencyEndpoints, IdempotencyProperties properties) {
		Assert.notNull(redisTemplate, "redisTemplate is required");
		Assert.notEmpty(idempotencyEndpoints.getUrlPatterns(), "No Pattterns are configured");
		this.idempotencyProperties = properties;
		this.redisTemplate = redisTemplate;
		this.idempotencyEndpoints = idempotencyEndpoints;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("IdempontecyFilter initialize");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (idempotencyEndpoints.isApplicable(request)) {
			executeIdempotency(chain, request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	private void executeIdempotency(FilterChain chain, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			String key = getKey(request, this.idempotencyEndpoints);
			LOGGER.info("try lock request key: {}", key);
			Boolean notExistInRedis = redisTemplate.opsForValue().setIfAbsent(key, new StoredResponse(), idempotencyProperties.getIdempotencyTtl(), TimeUnit.MILLISECONDS);
			if (notExistInRedis != null) {
				if (notExistInRedis) {
					excecuteRequest(chain, request, response, key);
				} else {
					excecuteIdempotency(response, request, key);
				}
			} else {
				LOGGER.error(ERROR_REDIS, key);
				throw new RedisException(String.format(ERROR_REDIS, key));
			}
		} catch (ArgumentNotFoundException e) {
			writeBadRequestMessage(response, request, e);
		} catch (Exception e) {
			LOGGER.error("Error to parser", e);
		}
	}

	private void excecuteRequest(FilterChain chain, HttpServletRequest request, HttpServletResponse response, String key) {
		LOGGER.info("firts Time for these request, lock key: {}", key);
		try {
			HttpServletResponseCopier responseCopier = new HttpServletResponseCopier(response);
			LOGGER.info("Call the real controller");
			chain.doFilter(request, responseCopier);
			byte[] copy = responseCopier.getCopy();
			String value = new String(copy);
			StoredResponse storeObject = new StoredResponse(value, new HeadersUtil().getHeadersMap(response), response.getStatus(), false);
			LOGGER.info("store request key: {} - value: {}", key, storeObject);
			redisTemplate.opsForValue().set(key, storeObject);
			LOGGER.info("set expiration - request key: {} ttl: ", key);
			redisTemplate.expire(key, idempotencyProperties.getIdempotencyTtl(), TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			LOGGER.error("Error to save idempotency response in redis", e);
		}
	}

	private void excecuteIdempotency(HttpServletResponse response, HttpServletRequest request, String key) throws IOException, ServletException {
		//non firts Time for these key
		StoredResponse previusResponse = (StoredResponse) redisTemplate.opsForValue().get(key);
		LOGGER.info("retrive key: {} to cache, body: {}", key, previusResponse);
		if (previusResponse != null) {
			if (!previusResponse.isLocked()) {
				writeResponse(response, previusResponse, true);
			} else {
				writeLockMessage(response, request);
			}
		} else {
			LOGGER.error(ERROR_REDIS, key);
			throw new RedisException(String.format(ERROR_REDIS, key));
		}
	}

	private void writeBadRequestMessage(HttpServletResponse response, HttpServletRequest request, ArgumentNotFoundException e) throws IOException, ServletException {
		//Not key present bad request
		RestException message = new BadRequestException(idempotencyProperties.getBadRequestCode(), e.getArgument() + " Not Found", Optional.empty());
		writeErrorMessage(response, message, request, HttpStatus.BAD_REQUEST);
	}

	private void writeLockMessage(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
		//its locked return 409 connflict idempotency
		RestException message = new ConflictException(idempotencyProperties.getConflictCode(), idempotencyProperties.getConflictMessage(), Optional.empty());
		writeErrorMessage(response, message, request, HttpStatus.CONFLICT);
	}

	private void writeErrorMessage(HttpServletResponse response, RestException exception, HttpServletRequest request, HttpStatus status) throws IOException {
		writeResponse(response, new StoredResponse(new JsonUtils().objectToJsonString(exception.getErrorMessage(request)), null, status.value()), false);
	}

	private String getKey(HttpServletRequest request, IdempotencyEndpoints idempotencyEndpoints) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return idempotencyEndpoints.generateKey(request);
	}

	private HttpServletResponseCopier writeResponse(HttpServletResponse response, StoredResponse message, boolean cached) throws IOException {
		HttpServletResponseCopier wrapper = new HttpServletResponseCopier(response);
		PrintWriter responseWriter = response.getWriter();
		CharArrayWriter charWriter = new CharArrayWriter();
		charWriter.write(message.getBody());
		String alteredContent = charWriter.toString();
		response.setContentLength(alteredContent.length());
		response.setStatus(message.getStatusCode());
		response.setHeader("Content-Type", "application/json");
		response.setHeader("X-Idempotency", String.valueOf(cached));
		responseWriter.write(alteredContent);
		return wrapper;
	}


	@Override
	public void destroy() {
		// empty
	}
}
