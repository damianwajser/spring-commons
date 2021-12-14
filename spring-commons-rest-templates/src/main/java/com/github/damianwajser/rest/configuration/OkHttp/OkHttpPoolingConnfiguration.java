package com.github.damianwajser.rest.configuration.OkHttp;

import com.github.damianwajser.rest.configuration.properties.PoolConfigurationProperties;
import okhttp3.ConnectionPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty(prefix = "spring.commons.rest.template", name = "implementation", havingValue = "OK_HTTP")
public class OkHttpPoolingConnfiguration {
	
	@Bean
	public ConnectionPool poolingHttpClientConnectionManager(PoolConfigurationProperties poolConfigurationProperties) {
		ConnectionPool result = new ConnectionPool(poolConfigurationProperties.getMaxTotal(), 5, TimeUnit.MINUTES);
		return result;
	}
}
