package com.github.damianwajser.rest.interceptors;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

	private Optional<HttpServletRequest> getCurrentHttpRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).filter(
				requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
				.map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
				.map(ServletRequestAttributes::getRequest);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		HttpServletRequest servletRequest = getCurrentHttpRequest().get();
		Enumeration<String> headers = servletRequest.getHeaderNames();
		while (headers.hasMoreElements()) {
			String headerName = headers.nextElement();
			String headerValue = servletRequest.getHeader(headerName);
			if (headerValue != null && headerName.toUpperCase().startsWith("X-")) {
				request.getHeaders().add(headerName, headerValue);
			}
		}
		ClientHttpResponse response = execution.execute(request, body);
		return response;
	}
}
