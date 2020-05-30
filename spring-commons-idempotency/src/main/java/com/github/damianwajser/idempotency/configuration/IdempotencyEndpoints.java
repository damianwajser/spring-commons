package com.github.damianwajser.idempotency.configuration;

import com.github.damianwajser.idempotency.generators.DefaultIdempotencyKeyGenerator;
import com.github.damianwajser.idempotency.generators.IdempotencyKeyGenerator;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

	public IdempotencyEndpoint addIdempotencyEndpoint(String endpoint, IdempotencyKeyGenerator<Object> keyGenerator, HttpMethod... methods) {
		IdempotencyEndpoint idempotencyEndpoint = new IdempotencyEndpoint(endpoint, new HashSet<>(Arrays.asList(methods)), keyGenerator);
		this.endpoints.put(endpoint, idempotencyEndpoint);
		return idempotencyEndpoint;
	}

	public String[] getUrlPatterns() {
		return endpoints.keySet().toArray(new String[endpoints.keySet().size()]);
	}

	private IdempotencyEndpoint getEndpoint(HttpServletRequest request) {
		return this.endpoints.get(request.getRequestURI());
	}

	public String generateKey(HttpServletRequest request) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		return this.getEndpoint(request).generateKey(request);
	}

	public boolean isApplicable(HttpServletRequest request) {
		return this.getEndpoint(request).isAppicable(HttpMethod.valueOf(request.getMethod()));
	}
}
