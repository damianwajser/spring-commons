package com.github.damianwajser.rest.interceptors;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateInterceptor.class);

	private Optional<HttpServletRequest> getCurrentHttpRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).filter(
						requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
				.map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
				.map(ServletRequestAttributes::getRequest);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// get current http call
		Optional<HttpServletRequest> currentOptionalRequest = getCurrentHttpRequest();
		// is in context of an http call
		if (currentOptionalRequest.isPresent()) {
			HttpServletRequest currentRequest = currentOptionalRequest.get();
			Enumeration<String> headers = currentRequest.getHeaderNames();
			while (headers.hasMoreElements()) {
				String headerName = headers.nextElement();
				String headerValue = currentRequest.getHeader(headerName);
				//if current request header is valid, add to new request
				if (isValidHeader(request, headerName, headerValue)) {
					LOGGER.debug("add headers: {}: {}", headerName, headerValue);
					request.getHeaders().add(headerName, Encode.forJava(headerValue));
				}
			}
		}
		return execution.execute(request, body);
	}

	private boolean isValidHeader(HttpRequest request, String headerName, String headerValue) {
		return headerValue != null
				&& isCustomHHeader(headerName)
				&& isValidValue(headerName, headerValue, request);
	}

	private boolean isValidValue(String headerName, String headerValue, HttpRequest request) {
		// Check that you do not want to overwrite the header,
		// for it to be valid the current value must be null
		// or not contain what I want to add
		List<String> headers = request.getHeaders().get(headerName);
		return headers == null || !headers.contains(headerValue);
	}

	private boolean isCustomHHeader(String headerName) {
		return headerName.toUpperCase().startsWith("X-");
	}
}
