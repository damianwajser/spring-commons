package com.github.damianwajser.idempotency.configuration;

import com.github.damianwajser.idempotency.generators.DefaultIdempotencyKeyGenerator;
import com.github.damianwajser.idempotency.generators.IdempotencyKeyGenerator;
import com.github.damianwajser.idempotency.utils.HeadersUtil;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class IdempotencyEndpoint {

	private String endpoint;
	private Set<HttpMethod> methods;
	private final IdempotencyKeyGenerator generator;

	public IdempotencyEndpoint(String endpoint, Set<HttpMethod> methods) {
		this(endpoint, methods, new DefaultIdempotencyKeyGenerator());
	}

	public IdempotencyEndpoint(String endpoint, Set<HttpMethod> methods, IdempotencyKeyGenerator generator) {
		this.methods = methods;
		this.endpoint = endpoint;
		this.generator = generator;
		Assert.notNull(generator, "Genenerator cant be null");
	}

	public String generateKey(HttpServletRequest request) {
		return generator.generateKey(new HeadersUtil().getHeaders(request),
				HttpMethod.valueOf(request.getMethod()), Encode.forJava(request.getRequestURI()), request);
	}

	public boolean isAppicable(HttpMethod httpMethod) {
		return this.methods.contains(httpMethod);
	}
}
