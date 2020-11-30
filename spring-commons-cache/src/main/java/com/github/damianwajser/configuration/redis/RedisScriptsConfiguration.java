package com.github.damianwajser.configuration.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.ArrayList;

@Configuration
@ConditionalOnProperty(name = "spring.commons.cache.enabled", havingValue = "true")
public class RedisScriptsConfiguration {
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
}
