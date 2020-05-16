package com.github.damianwajser.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.damianwajser.configuration.MDCProperties;

@Component
public abstract class MDCFilter implements Filter, MDCProperties {

	@Value("${logstash.trace.id.key:X-Trace-Id}")
	private String traceId;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// Setup MDC data:
			// Referenced in Spring Boot's logging.pattern.level property
			MDC.put("clientIp", request.getRemoteAddr());
			if (HttpServletRequest.class.isAssignableFrom(request.getClass())) {
				MDC.put("requestId", requestId(((HttpServletRequest) request)));
				Enumeration<String> headers = ((HttpServletRequest) request).getHeaderNames();
				while (headers.hasMoreElements()) {
					String headerName = headers.nextElement();
					String headerValue = ((HttpServletRequest) request).getHeader(headerName);
					if (headerValue != null && headerName.toUpperCase().startsWith("X-")) {
						MDC.put(headerName, headerValue);
					}
				}
			}
			getProperties().keySet().forEach(property -> MDC.put(property, getProperties().get(property)));
			chain.doFilter(request, response);
		} finally {
			MDC.clear();
		}
	}

	@Override
	public void destroy() {
		MDC.clear();
	}

	protected String requestId(HttpServletRequest request) {
		String traceValue = request.getHeader(traceId);
		if (StringUtils.isEmpty(traceValue)) {
			traceValue = UUID.randomUUID().toString();
		}
		return traceValue;
	}
}
