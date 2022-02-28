package com.github.damianwajser.web.filters;

import com.github.damianwajser.web.configuration.PropertiesHttpFixer;
import org.apache.catalina.connector.ResponseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ConditionalOnProperty(name = "spring.commons.http.fixer.enabled", havingValue = "true", matchIfMissing = true)
public class FixHttpPostFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FixHttpPostFilter.class);

	@Autowired
	private PropertiesHttpFixer props;

	@Override
	public void destroy() {
		LOGGER.debug("destroy filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
			httpServletResponse.setStatus(HttpStatus.CREATED.value());
		}
		chain.doFilter(request, response);// continue execution of other filter chain.
		if (ResponseFacade.class.isInstance(httpServletResponse) && ResponseFacade.class.cast(httpServletResponse).getContentWritten() < 1) {
			httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("innit filter");
	}
}
