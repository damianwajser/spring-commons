package com.github.damianwajser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

	@Autowired
	private Servicet servicet;


	@PostMapping("/cache")
	private String test_cache() {
		return servicet.generateUUID();
	}

}
