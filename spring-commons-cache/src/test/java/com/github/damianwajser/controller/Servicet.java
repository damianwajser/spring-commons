package com.github.damianwajser.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Servicet {

	@Cacheable(value = "somecache")
	public String generateUUID() {
		return UUID.randomUUID().toString();
	}
}
