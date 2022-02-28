package com.github.damianwajser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties
@PropertySource("classpath:spring-commons-logger.properties")
public class PropertiesLogger {

	public static final String APP_NAME = "APP_NAME";
	private static final Map<String, String> propertiesToShow = new HashMap<>();
	@Value("${spring.commons.logger.trace.id}")
	private String traceId;

	@Value("${spring.commons.logger.app.name}")
	private String appName;

	@Value("${spring.commons.logger.payload.max}")
	private Integer maxPayLoad;

	public Integer getMaxPayLoad() {
		return maxPayLoad;
	}

	public String getTraceId() {
		return traceId;
	}

	public String getAppName() {
		return appName;
	}

	public Map<String, String> getPropetiesToShow() {
		if (propertiesToShow.isEmpty()) {
			propertiesToShow.put(APP_NAME, this.getAppName());
		}
		return propertiesToShow;
	}
}
