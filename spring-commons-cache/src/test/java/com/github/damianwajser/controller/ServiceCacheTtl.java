package com.github.damianwajser.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceCacheTtl {

	@Cacheable(value = "cache-ttl1")
	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	@Cacheable(value = "cache-ttl2")
	public String generateUUID2() {
		return UUID.randomUUID().toString();
	}

	@Cacheable(value = "cache-ttl3")
	public String generateUUID3() {
		return UUID.randomUUID().toString();
	}
}
