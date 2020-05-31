package com.github.damianwajser.idempotency.generators;

import com.github.damianwajser.idempotency.exception.ArgumentNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultIdempotencyKeyGenerator implements IdempotencyKeyGenerator<Object> {

	private static final String IDEMPOTENCY_DEFALUT_HEADER = "X-Idempotency-Key";

	@Override
	public String generateKey(HttpHeaders headers, HttpMethod method, String path, Object request) {
		String key = getHeaderValue(headers, IDEMPOTENCY_DEFALUT_HEADER);
		return path + key + "-" + method.toString();
	}

	protected String getHeaderValue(HttpHeaders headers, String headerKey) {
		List<String> idempotencyHeader = headers.get(headerKey);
		String key;
		if (idempotencyHeader != null) {
			key = idempotencyHeader.stream().collect(Collectors.joining("-"));
		} else {
			throw new ArgumentNotFoundException(headerKey);
		}
		return key;
	}
}
