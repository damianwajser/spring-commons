package com.github.damianwajser.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;

@Configuration
public class CommonsConfiguration {
	@Autowired
	private PropertiesLogger propertiesLogger;
	@Bean
	public Filter logFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(propertiesLogger.getMaxPayLoad());

		return filter;
	}
}
