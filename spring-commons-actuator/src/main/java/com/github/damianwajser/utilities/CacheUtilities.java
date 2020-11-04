package com.github.damianwajser.utilities;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.stream.Collectors;

public final class CacheUtilities {
	public static Map<String, Object> getKeysInformation(RedisTemplate redisTemplate, String cache) {
		Set<Object> keys = redisTemplate != null ? redisTemplate.keys(cache + "*") : new HashSet<>();
		return keys.isEmpty() ? new HashMap<>() : keys.stream().collect(Collectors.toMap(Object::toString, k -> redisTemplate.opsForValue().get(k)));
	}

	public static Optional<RedisCache> getRedisCache(CacheManager cacheManager, String name) {
		Optional<RedisCache> redisCache = Optional.empty();
		if (cacheManager != null) {
			Cache cacheImpl = cacheManager.getCache(name);
			if (TransactionAwareCacheDecorator.class.isAssignableFrom(cacheImpl.getClass())) {
				cacheImpl = ((TransactionAwareCacheDecorator) cacheImpl).getTargetCache();
				if (RedisCache.class.isAssignableFrom(cacheImpl.getClass())) {
					redisCache = Optional.of((RedisCache) cacheImpl);
				}
			}
		}
		return redisCache;
	}
}
