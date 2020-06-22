package com.github.damianwajser.actuator.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.cache.CachesEndpointWebExtension;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Primary
@Component
@EndpointWebExtension(endpoint = CachesEndpoint.class)
public class CacheEndpointExtension extends CachesEndpointWebExtension {
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheEndpointExtension.class);
	@Autowired
	private CacheManager cache;
	@Autowired
	private RedisTemplate redisTemplate;

	private final CachesEndpoint delegate;

	public CacheEndpointExtension(CachesEndpoint delegate) {
		super(delegate);
		LOGGER.debug("inicializando extencion actuator");
		this.delegate = delegate;
	}

	@ReadOperation
	public WebEndpointResponse<Map> cache(@Selector String cache, @Selector String detail, @Nullable String cacheManager) {
		LOGGER.info("call to cache extension");
		WebEndpointResponse<CachesEndpoint.CacheEntry> cacheEntry = super.cache(cache, cacheManager);
		Map<String, Object> info = new HashMap();
		if (cacheEntry.getStatus() == WebEndpointResponse.STATUS_OK) {
			Optional<RedisCache> redisCache = getRedisCache(cacheEntry.getBody().getName());
			redisCache.ifPresent((c) -> {
				info.put("ttl", c.getCacheConfiguration().getTtl().getSeconds() + " seconds");
				String prefix = c.getCacheConfiguration().getKeyPrefixFor(cache);
				info.put("prefix", prefix);
				info.put("keys", getKeysInformation(cache));
			});
		}
		return new WebEndpointResponse<>(info, 200);
	}

	private Object getKeysInformation(String cache) {
		Set keys = redisTemplate.keys(cache + "*");
		return keys.isEmpty() ? null : keys.stream().collect(Collectors.toMap(Object::toString, k -> redisTemplate.opsForValue().get(k)));
	}

	private Optional<RedisCache> getRedisCache(String name) {
		Optional<RedisCache> cache = Optional.empty();
		Cache cacheImpl = this.cache.getCache(name);
		if (TransactionAwareCacheDecorator.class.isAssignableFrom(cacheImpl.getClass())) {
			cacheImpl = ((TransactionAwareCacheDecorator) cacheImpl).getTargetCache();
			if (RedisCache.class.isAssignableFrom(cacheImpl.getClass())) {
				cache = Optional.of((RedisCache) cacheImpl);
			}
		}
		return cache;
	}

}
