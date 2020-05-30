package com.github.damianwajser.idempotency.utils;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HeadersUtil {

	public HttpHeaders getHeaders(HttpServletRequest request) {
		return Collections.list(request.getHeaderNames()).stream()
				.collect(Collectors.toMap(
						Function.identity(),
						h -> Collections.list(request.getHeaders(h)),
						(oldValue, newValue) -> newValue,
						HttpHeaders::new
				));
	}

	public Map<String, String> getHeadersMap(HttpServletResponse response) {
		return response.getHeaderNames()
				.stream()
				.collect(Collectors.toMap(
						Function.identity(),
						h -> response.getHeaders(h).stream().collect(Collectors.joining(","))
				));
	}
}
