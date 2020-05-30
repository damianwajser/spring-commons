package com.github.damianwajser.idempotency.configuration;

import com.github.damianwajser.idempotency.filters.IdempontecyFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;

@Configuration
@ConditionalOnProperty(name = "spring.commons.idempotency.enabled", havingValue = "true")
public class IdempontencyFilterConnfiguration {

	@Bean
	@ConditionalOnProperty(name = "spring.commons.idempotency.enabled", havingValue = "true")
	public FilterRegistrationBean<Filter> someFilterRegistration(IdempotencyEndpoints idempotencyEndpoints, IdempotencyProperties idempotencyProperties, RedisTemplate redisTemplate) {

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(someFilter(idempotencyEndpoints, idempotencyProperties, redisTemplate));
		registration.addUrlPatterns(idempotencyEndpoints.getUrlPatterns());
		registration.setName("IdempontecyFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	@Bean(name = "IdempontecyFilter")
	@ConditionalOnProperty(name = "spring.commons.idempotency.enabled", havingValue = "true")
	public Filter someFilter(IdempotencyEndpoints idempotencyEndpoints, IdempotencyProperties idempotencyProperties, RedisTemplate redisTemplate) {
		return new IdempontecyFilter(redisTemplate, idempotencyEndpoints, idempotencyProperties);
	}
}
