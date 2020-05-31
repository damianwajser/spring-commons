package com.github.damianwajser.idempotency.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.damianwajser.idempotency.generators.DefaultIdempotencyKeyGenerator;
import com.github.damianwajser.idempotency.generators.IdempotencyKeyGenerator;
import com.github.damianwajser.idempotency.utils.HeadersUtil;
import org.apache.commons.io.IOUtils;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

public class IdempotencyEndpoint {

	private String endpoint;
	private Set<HttpMethod> methods;
	private final IdempotencyKeyGenerator<Object> genarator;

	public IdempotencyEndpoint(String endpoint, Set<HttpMethod> methods) {
		this(endpoint, methods, new DefaultIdempotencyKeyGenerator());
	}

	public IdempotencyEndpoint(String endpoint, Set<HttpMethod> methods, IdempotencyKeyGenerator<Object> generator) {
		this.methods = methods;
		this.endpoint = endpoint;
		this.genarator = generator;
		Assert.notNull(generator, "Genenerator cant be null");
	}

	public String generateKey(HttpServletRequest request) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		String reqStr = IOUtils.toString(request.getReader());
		Object req = null;
		Class<?> clazz = getGenericTypeClass(genarator.getClass());
		if (!StringUtils.isEmpty(reqStr)) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			req = mapper.readValue(reqStr, clazz);
		} else {
			req = clazz.getDeclaredConstructor().newInstance();
		}
		return genarator.generateKey(new HeadersUtil().getHeaders(request),
				HttpMethod.valueOf(request.getMethod()), Encode.forJava(request.getRequestURI()), req);
	}

	@SuppressWarnings("unchecked")
	private Class<?> getGenericTypeClass(Class<?> clazz) {
		try {
			String className = ((ParameterizedType) clazz.getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
			return Class.forName(className);
		} catch (Exception e) {
			throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
		}
	}

	public boolean isAppicable(HttpMethod httpMethod) {
		return this.methods.contains(httpMethod);
	}
}
