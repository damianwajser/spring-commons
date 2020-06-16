package com.github.damianwajser.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Service
public class ServiceCache {

	@Cacheable(value = "somecache2")
	public String generateUUID() {
		return UUID.randomUUID().toString();
	}

	@CacheEvict("somecache2")
	public String delete() {
		return null;
	}

	@Cacheable("customers_all")
	public Collection<Integer> getAll() {
		return Arrays.asList(2, 2);
	}

	@Cacheable("customers")
	public Integer getId(Integer companyId, Integer id) {
		return id;
	}

	@CacheEvict("customers_all")
	@CachePut("customers")
	public Integer update(Object companyId, Integer id) {
		return 2;
	}

	@Caching(evict = {
			@CacheEvict(value = "customers_all", allEntries = true),
			@CacheEvict(value = "customers")
	})
	public void delete(Integer companyId, Integer id) {
	}
}
