package com.github.damianwajser.idempotency.configuration;

import com.github.damianwajser.idempotency.exception.ArgumentNotFoundException;
import com.github.damianwajser.idempotency.generators.DefaultIdempotencyKeyGenerator;
import com.github.damianwajser.idempotency.generators.IdempotencyKeyGenerator;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class IdempotencyEndpoints {

	private Map<String, IdempotencyEndpoint> endpoints = new HashMap<>();

	public IdempotencyEndpoints() {
		// Need public constructor
	}

	public IdempotencyEndpoint addIdempotencyEndpoint(String endpoint) {
		return this.addIdempotencyEndpoint(endpoint, HttpMethod.POST);
	}

	public IdempotencyEndpoint addIdempotencyEndpoint(String endpoint, HttpMethod... methods) {
		return this.addIdempotencyEndpoint(endpoint, new DefaultIdempotencyKeyGenerator(), methods);
	}

	public IdempotencyEndpoint addIdempotencyEndpoint(String endpoint, IdempotencyKeyGenerator keyGenerator, HttpMethod... methods) {
		IdempotencyEndpoint idempotencyEndpoint = new IdempotencyEndpoint(new HashSet<>(Arrays.asList(methods)), keyGenerator);
		this.endpoints.put(endpoint, idempotencyEndpoint);
		return idempotencyEndpoint;
	}

	public String[] getUrlPatterns() {
		return endpoints.keySet().toArray(new String[endpoints.keySet().size()]);
	}

	private Optional<IdempotencyEndpoint> getEndpoint(HttpServletRequest request) {
		return this.endpoints.entrySet().stream()
				.filter(entry -> new AntPathMatcher().match(entry.getKey(), request.getRequestURI()))
				.map(Map.Entry::getValue)
				.findFirst();
	}

	public String generateKey(HttpServletRequest request) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		return this.getEndpoint(request).map(ie -> ie.generateKey(request))
				.orElseThrow(() -> new ArgumentNotFoundException(request.getRequestURI()));
	}

	public boolean isApplicable(HttpServletRequest request) {
		return this.getEndpoint(request).map(e -> e.isAppicable(HttpMethod.valueOf(request.getMethod()))).orElse(false);
	}
}
