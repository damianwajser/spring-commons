package com.github.damianwajser.filter;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@ConditionalOnProperty(name = "logstash.duration.request.enabled", havingValue = "true")
public class StatsFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatsFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("init stats filter");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		long time = System.currentTimeMillis();
		try {
			chain.doFilter(req, resp);
		} finally {
			time = System.currentTimeMillis() - time;
			HttpServletRequest request = ((HttpServletRequest) req);
			String uri = Encode.forJava(request.getRequestURI());
			LOGGER.info("{} {}: {} ms", request.getMethod(), uri, time);
		}
	}

	@Override
	public void destroy() {
		// empty
	}
}
