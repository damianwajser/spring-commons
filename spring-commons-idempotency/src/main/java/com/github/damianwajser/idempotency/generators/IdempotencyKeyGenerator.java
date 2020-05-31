package com.github.damianwajser.idempotency.generators;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public interface IdempotencyKeyGenerator<T> {
	String generateKey(HttpHeaders headers, HttpMethod method, String path, T body);
}
