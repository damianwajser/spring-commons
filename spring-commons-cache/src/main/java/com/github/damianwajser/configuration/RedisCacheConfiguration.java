package com.github.damianwajser.configuration;

import com.github.damianwajser.serializer.CustomJdkSerializationRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.ArrayList;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.commons.cache.enabled", havingValue = "true")
public class RedisCacheConfiguration {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);

		redisTemplate.setDefaultSerializer(new CustomJdkSerializationRedisSerializer());
		redisTemplate.setHashKeySerializer(new CustomJdkSerializationRedisSerializer());
		redisTemplate.setHashValueSerializer(new CustomJdkSerializationRedisSerializer());
		redisTemplate.setKeySerializer(new CustomJdkSerializationRedisSerializer());
		redisTemplate.setValueSerializer(new CustomJdkSerializationRedisSerializer());

		return redisTemplate;
	}

	@Bean(name = "scriptCheckAndSetHash")
	public RedisScript<ArrayList> scriptCheckAndSetHash() {
		DefaultRedisScript<ArrayList> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("checkAndSetHash.lua")));
		redisScript.setResultType(ArrayList.class);

		return redisScript;
	}

	@Bean(name = "scriptCheckAndSetHashKeys")
	public RedisScript<ArrayList> scriptCheckAndSetHashKeys() {
		DefaultRedisScript<ArrayList> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("checkAndSetHashKeys.lua")));
		redisScript.setResultType(ArrayList.class);

		return redisScript;
	}

	@Bean(name = "scriptCheckAndIncrementHash")
	public RedisScript<ArrayList> scriptCheckAndIncrementHash() {
		DefaultRedisScript<ArrayList> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("checkAndIncrementHash.lua")));
		redisScript.setResultType(ArrayList.class);

		return redisScript;
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory jedisConnectionFactory) {
		RedisCacheManager rcm = RedisCacheManager.create(jedisConnectionFactory);
		rcm.setTransactionAware(true);
		return rcm;
	}

	@Bean
	@ConditionalOnProperty("redis.listener.enabled")
	public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory);
		return listenerContainer;
	}

}
