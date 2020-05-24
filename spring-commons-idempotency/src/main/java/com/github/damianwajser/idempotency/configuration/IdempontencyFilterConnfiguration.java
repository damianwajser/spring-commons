package com.github.damianwajser.idempotency.configuration;

import com.github.damianwajser.idempotency.filters.IdempontecyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;

@Configuration
public class IdempontencyFilterConnfiguration {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private IdempotencyProperties idempotencyProperties;

	@Bean
	@ConditionalOnProperty(name = "spring.commons.idempotency.enabled", havingValue = "true")
	public FilterRegistrationBean someFilterRegistration(IdempotencyEndpoints idempotencyEndpoints) {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(someFilter(idempotencyEndpoints));
		registration.addUrlPatterns(idempotencyEndpoints.getUrlPatterns());
		registration.setName("IdempontecyFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	@Bean(name = "IdempontecyFilter")
	@ConditionalOnProperty(name = "spring.commons.idempotency.enabled", havingValue = "true")
	public Filter someFilter(IdempotencyEndpoints idempotencyEndpoints) {
		return new IdempontecyFilter(redisTemplate, idempotencyEndpoints, idempotencyProperties);
	}
}
