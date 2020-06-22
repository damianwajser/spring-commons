package com.github.damianwajser.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;

@Configuration
public class CommonsConfiguration {

	@Bean
	public Filter logFilter(PropertiesLogger propertiesLogger) {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(propertiesLogger.getMaxPayLoad());

		return filter;
	}
}
