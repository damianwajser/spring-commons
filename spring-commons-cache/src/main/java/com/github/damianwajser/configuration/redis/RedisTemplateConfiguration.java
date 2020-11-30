package com.github.damianwajser.configuration.redis;

import com.github.damianwajser.serializer.CustomJdkKeyPrefixRedisSerializer;
import com.github.damianwajser.serializer.CustomJdkRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@ConditionalOnProperty(name = "spring.commons.cache.enabled", havingValue = "true")
public class RedisTemplateConfiguration {

	@Value("${spring.commons.cache.prefix.enabled:true}")
	private boolean prefixEnabled;

	@Value("${spring.commons.cache.prefix.value}")
	private String prefix;

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setDefaultSerializer(new CustomJdkRedisSerializer());
		if (prefixEnabled) {
			String prefixSeparator = prefix + "::";
			redisTemplate.setHashKeySerializer(new CustomJdkKeyPrefixRedisSerializer(prefixSeparator));
			redisTemplate.setKeySerializer(new CustomJdkKeyPrefixRedisSerializer(prefixSeparator));
		} else {
			redisTemplate.setHashKeySerializer(new CustomJdkRedisSerializer());
			redisTemplate.setKeySerializer(new CustomJdkRedisSerializer());
		}
		redisTemplate.setHashValueSerializer(new CustomJdkRedisSerializer());
		redisTemplate.setValueSerializer(new CustomJdkRedisSerializer());

		return redisTemplate;
	}
}
