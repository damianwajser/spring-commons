package com.github.damianwajser.rest.configuration;

import com.github.damianwajser.rest.configuration.properties.PoolConfigurationProperties;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpPoolingConnfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpPoolingConnfiguration.class);

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(PoolConfigurationProperties poolConfigurationProperties) {
		PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
		LOGGER.info("set max Total http pools in rest client: {}", poolConfigurationProperties.getMaxTotal());
		result.setMaxTotal(poolConfigurationProperties.getMaxTotal());
		LOGGER.info("set MaxDefaultPerRoute http pools in rest client: {}", poolConfigurationProperties.getMaxDefaultPerRoute());
		result.setDefaultMaxPerRoute(poolConfigurationProperties.getMaxDefaultPerRoute());
		return result;
	}
}
