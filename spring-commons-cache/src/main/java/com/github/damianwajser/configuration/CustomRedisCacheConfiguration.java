package com.github.damianwajser.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.commons.cache.enabled", havingValue = "true")
public class CustomRedisCacheConfiguration {

	@Autowired
	private TtlProperties prop;

	@Value("${spring.commons.cache.prefix.enabled:true}")
	private boolean prefixEnabled;

	@Value("${spring.commons.cache.prefix.value}")
	private String prefix;

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory jedisConnectionFactory) {
		RedisCacheManager rcm = null;
		if (prefixEnabled) {
			rcm = this.getPrefixManager(jedisConnectionFactory);
		} else {
			rcm = RedisCacheManager.create(jedisConnectionFactory);
		}
		rcm.setTransactionAware(true);
		return rcm;
	}

	private RedisCacheManager getPrefixManager(RedisConnectionFactory jedisConnectionFactory) {
		return RedisCacheManager.builder(jedisConnectionFactory)
				.cacheDefaults(getDefaultConnfig())
				.withInitialCacheConfigurations(this.getCacheConfigurations())
				.build();
	}

	private Map<String, RedisCacheConfiguration> getCacheConfigurations() {
		HashMap<String, RedisCacheConfiguration> caches = new HashMap<>();
		prop.getTtls().forEach((k, v) -> caches.put(k, getDefaultConnfig().entryTtl(Duration.ofSeconds(v))));
		return caches;
	}

	private RedisCacheConfiguration getDefaultConnfig() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(prop.getGenericTTl()))
				.computePrefixWith(cacheName -> prefix + "::" + CacheKeyPrefix.simple().compute(cacheName));
	}

}
