package com.github.damianwajser.configuration;

import com.github.damianwajser.idempotency.exception.ArgumentNotFoundException;
import com.github.damianwajser.idempotency.generators.IdempotencyKeyGenerator;
import com.github.damianwajser.model.FooObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.stream.Collectors;

public class FooIdempotencyKeyGenerator<T> implements IdempotencyKeyGenerator<FooObject> {

	private static final String IDEMPOTENCY_DEFALUT_HEADER = "X-Idempotency-Key";

	@Override
	public String generateKey(HttpHeaders headers, HttpMethod method, String path, FooObject request) {
		String key = getHeaderValue(headers, IDEMPOTENCY_DEFALUT_HEADER);
		return path + "-" + key + "-" + method.toString() + "-" + request.getValue();
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
