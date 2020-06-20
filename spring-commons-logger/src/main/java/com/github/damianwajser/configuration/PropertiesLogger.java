package com.github.damianwajser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource("classpath:spring-commons-logger.properties")
public class PropertiesLogger {

	@Value("${spring.commons.logger.trace.id}")
	private String traceId;

	@Value("${spring.commons.logger.app.name}")
	private String appName;

	@Value("${spring.commons.logger.payload.max}")
	private Integer maxPayLoad;

	public Integer getMaxPayLoad() {
		return maxPayLoad;
	}

	public void setMaxPayLoad(Integer maxPayLoad) {
		this.maxPayLoad = maxPayLoad;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
