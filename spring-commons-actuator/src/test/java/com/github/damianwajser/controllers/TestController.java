package com.github.damianwajser.controllers;

import com.github.damianwajser.model.FooObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	private RedisTemplate redisTemplate;

	@PostMapping("/postok")
	private FooObject postok() {
		return new FooObject("something");
	}

	@GetMapping("/")
	private String get() {
		redisTemplate.opsForValue().set("key", "value");
		return "";
	}
}
