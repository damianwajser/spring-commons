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
		Optional<HttpServletRequest> currentRequest = getCurrentHttpRequest();
		if (currentRequest.isPresent()) {
			HttpServletRequest servletRequest = currentRequest.get();
			Enumeration<String> headers = servletRequest.getHeaderNames();
			while (headers.hasMoreElements()) {
				String headerName = headers.nextElement();
				String headerValue = servletRequest.getHeader(headerName);
				if (headerValue != null && headerName.toUpperCase().startsWith("X-")) {
					LOGGER.debug("add headers: {}: {}", headerName, headerValue);
					request.getHeaders().add(headerName, Encode.forJava(headerValue));
				}
			}
		}
		return execution.execute(request, body);
	}
}
