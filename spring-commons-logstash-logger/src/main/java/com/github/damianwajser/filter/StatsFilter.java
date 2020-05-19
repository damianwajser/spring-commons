package com.github.damianwajser.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@WebFilter("/*")
@ConditionalOnProperty(name = "logstash.duration.request.enabled", havingValue = "true")
public class StatsFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatsFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
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
			LOGGER.info("{} {}: {} ms", request.getMethod(), request.getRequestURI(), time);
		}
	}

	@Override
	public void destroy() {
		// empty
	}
}