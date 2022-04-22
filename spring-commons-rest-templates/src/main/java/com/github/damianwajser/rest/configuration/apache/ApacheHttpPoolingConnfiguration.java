package com.github.damianwajser.rest.configuration.apache;

import com.github.damianwajser.rest.configuration.properties.PoolConfigurationProperties;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.commons.rest.template.implementation", havingValue = "HTTP_CLIENT", matchIfMissing = true)
public class ApacheHttpPoolingConnfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApacheHttpPoolingConnfiguration.class);

	@Autowired(required = false)
	private Registry<ConnectionSocketFactory> registry;

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(PoolConfigurationProperties poolConfigurationProperties) {
		PoolingHttpClientConnectionManager result = getPoolingHttpConnection();
		LOGGER.info("set max Total http pools in rest client: {}", poolConfigurationProperties.getMaxTotal());
		result.setMaxTotal(poolConfigurationProperties.getMaxTotal());
		LOGGER.info("set MaxDefaultPerRoute http pools in rest client: {}", poolConfigurationProperties.getMaxDefaultPerRoute());
		result.setDefaultMaxPerRoute(poolConfigurationProperties.getMaxDefaultPerRoute());
		return result;
	}

	private PoolingHttpClientConnectionManager getPoolingHttpConnection() {
		return registry==null?new PoolingHttpClientConnectionManager():new PoolingHttpClientConnectionManager(registry);
	}

}
