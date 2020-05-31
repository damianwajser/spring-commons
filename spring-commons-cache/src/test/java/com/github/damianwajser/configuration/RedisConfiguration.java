package com.github.damianwajser.configuration;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.Duration;

@TestConfiguration
@EnableRedisRepositories
public class RedisConfiguration {

	@Bean
	public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getHost(),
				redisProperties.getPort());
		JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder().readTimeout(Duration.ofMillis(0)).
				connectTimeout(Duration.ofMillis(0)).build();
		return new JedisConnectionFactory(config, clientConfiguration);
	}

}
