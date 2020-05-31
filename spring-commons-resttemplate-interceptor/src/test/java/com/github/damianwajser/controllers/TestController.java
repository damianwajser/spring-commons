package com.github.damianwajser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/replayheaders")
	private Object test1() {
		return restTemplate.getForObject("https://httpbin.org/get", Object.class);
	}
}
