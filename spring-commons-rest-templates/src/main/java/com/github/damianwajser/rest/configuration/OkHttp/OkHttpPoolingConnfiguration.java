package com.github.damianwajser.rest.configuration.OkHttp;

import com.github.damianwajser.rest.configuration.properties.PoolConfigurationProperties;
import okhttp3.ConnectionPool;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty(prefix = "spring.commons.rest.template", name = "implementation", havingValue = "OK_HTTP")
public class OkHttpPoolingConnfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpPoolingConnfiguration.class);

	@Bean
	public ConnectionPool poolingHttpClientConnectionManager(PoolConfigurationProperties poolConfigurationProperties) {
		ConnectionPool result = new ConnectionPool(poolConfigurationProperties.getMaxTotal(), 5, TimeUnit.MINUTES);
		return result;
	}
}
