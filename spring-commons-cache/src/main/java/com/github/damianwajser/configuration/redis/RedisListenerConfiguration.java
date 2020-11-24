package com.github.damianwajser.configuration.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@ConditionalOnProperty(name = "spring.commons.cache.enabled", havingValue = "true")
public class RedisListenerConfiguration {
	@Bean
	@ConditionalOnProperty("redis.listener.enabled")
	public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory);
		return listenerContainer;
	}
}
