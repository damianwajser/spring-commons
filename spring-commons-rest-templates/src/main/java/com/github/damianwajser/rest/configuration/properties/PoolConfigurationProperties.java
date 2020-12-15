package com.github.damianwajser.rest.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.commons.rest.template.http.connection.pool")
@PropertySource("classpath:spring-commons-http-pools.properties")
public class PoolConfigurationProperties {

	private int maxDefaultPerRoute;
	private int maxTotal;


	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxDefaultPerRoute() {
		return maxDefaultPerRoute;
	}

	public void setMaxDefaultPerRoute(int maxDefaultPerRoute) {
		this.maxDefaultPerRoute = maxDefaultPerRoute;
	}


}
