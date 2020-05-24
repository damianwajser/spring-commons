package com.github.damianwajser.configuration;

import com.github.damianwajser.idempotency.configuration.IdempotencyEndpoints;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@TestConfiguration
public class IdempotencyConfiguration {

	@Bean
	public IdempotencyEndpoints idempotencyEndpoints() {
		IdempotencyEndpoints idempotencyEndpoints = new IdempotencyEndpoints();
		idempotencyEndpoints.addIdempotencyEndpoint("/idempotency", new FooIdempotencyKeyGenerator(), HttpMethod.POST);
		idempotencyEndpoints.addIdempotencyEndpoint("/idempotency_delay");
		idempotencyEndpoints.addIdempotencyEndpoint("/idempotency_bad_request");
		return idempotencyEndpoints;
	}
}
