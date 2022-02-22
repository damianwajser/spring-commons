package com.github.damianwajser.configuration;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.idempotency.exception.ArgumentNotFoundException;
import com.github.damianwajser.idempotency.generators.IdempotencyKeyGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IdempotencyValidatorKeyGenerator implements IdempotencyKeyGenerator {

	private static final String IDEMPOTENCY_DEFAULT_HEADER = "X-Idempotency-Key";

	@Override
	public String generateKey(HttpHeaders headers, HttpMethod method, String path, HttpServletRequest request) throws RestException {
		throw new BadRequestException("code idempotency","error", Optional.empty());
	}
}
