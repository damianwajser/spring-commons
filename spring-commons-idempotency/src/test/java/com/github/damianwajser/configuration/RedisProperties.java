package com.github.damianwajser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
public class RedisProperties {
	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	private int redisPort;

	private String redisHost;

	public RedisProperties(
			@Value("${spring.redis.port:6379}") int redisPort,
			@Value("${spring.redis.host:localhost}") String redisHost) {
		this.redisPort = redisPort;
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}


}
