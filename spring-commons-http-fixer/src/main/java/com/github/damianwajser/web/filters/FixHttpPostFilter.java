package com.github.damianwajser.web.filters;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FixHttpPostFilter implements Filter {
	@Override
	public void destroy() {
		System.out.println("destroy filter. release our resources here if any");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
			httpServletResponse.setStatus(HttpStatus.CREATED.value());
		}
		chain.doFilter(request, response); // continue execution of other filter chain.
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
