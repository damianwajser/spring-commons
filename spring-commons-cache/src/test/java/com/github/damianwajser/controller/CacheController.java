package com.github.damianwajser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

	@Autowired
	private Servicet servicet;


	@GetMapping("/cache")
	private String get() {
		return servicet.generateUUID();
	}

	@DeleteMapping("/cache")
	private String post() {
		return servicet.delete();
	}


}
