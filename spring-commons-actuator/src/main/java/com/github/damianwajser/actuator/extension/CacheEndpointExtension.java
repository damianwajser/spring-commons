package com.github.damianwajser.actuator.extension;

import com.github.damianwajser.model.CacheInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.cache.CachesEndpointWebExtension;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.damianwajser.utilities.CacheUtilities.getKeysInformation;
import static com.github.damianwajser.utilities.CacheUtilities.getRedisCache;

@Primary
@Component
@EndpointWebExtension(endpoint = CachesEndpoint.class)
public class CacheEndpointExtension extends CachesEndpointWebExtension {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheEndpointExtension.class);

	@Autowired(required = false)
	private CacheManager cache;

	@Autowired(required = false)
	private RedisTemplate redisTemplate;

	public CacheEndpointExtension(CachesEndpoint delegate) {
		super(delegate);
		LOGGER.debug("initializing extension actuator");
	}

	@ReadOperation
	public WebEndpointResponse<CacheInfo> cache(@Selector String cacheName, @Selector String detail, @Nullable String cacheManager) {
		LOGGER.info("call to cache extension");
		WebEndpointResponse<CachesEndpoint.CacheEntry> cacheEntry = super.cache(cacheName, cacheManager);
		CacheInfo info = new CacheInfo();
		if (cacheEntry.getStatus() == WebEndpointResponse.STATUS_OK) {
			Optional<RedisCache> redisCache = getRedisCache(this.cache, cacheEntry.getBody().getName());
			redisCache.ifPresent(c -> {
				info.setTtl(c.getCacheConfiguration().getTtl().getSeconds() + " seconds");
				String prefix = c.getCacheConfiguration().getKeyPrefixFor(cacheName);
				info.setPrerfix(prefix);
				info.setKeys(getKeysInformation(redisTemplate, cacheName));
			});
		}
		LOGGER.info("response cache info {}", info);
		return new WebEndpointResponse<>(info, 200);
	}

}


