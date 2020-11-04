package com.github.damianwajser.actuator.enpoints;

import com.github.damianwajser.utilities.CacheUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Endpoint(id = "cache-keys")
public class CacheKeysEndpoint {
	@Autowired
	private RedisTemplate redisTemplate;

	@ReadOperation
	public Map<String, Object> keys() {
		return CacheUtilities.getKeysInformation(redisTemplate, "");
	}

	@ReadOperation
	public Map<String, Object> key(@Selector String key) {
		Map<String, Object> result = CacheUtilities.getKeysInformation(redisTemplate, key)
				.entrySet().stream()
				.collect(Collectors
						.toMap(entry -> entry.getKey(), entry -> new HashMap<String, Object>() {{
							put("value", entry.getValue());
							put("ttl", redisTemplate.opsForValue().getOperations().getExpire(entry.getKey()));
						}}));
		return result;
	}

	@DeleteOperation
	public boolean delete(@Selector String key) {
		return redisTemplate.opsForValue().getOperations().delete(key);
	}
}
