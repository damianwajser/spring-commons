package com.github.damianwajser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/echo")
	private Object test() {
		LOGGER.info("echo");
		return restTemplate.getForObject("https://httpbin.org/get", Object.class);
	}

	@GetMapping("/echo1")
	private Object echo() {
		LOGGER.info("echo1");
		return new FooObject("asdasd");
	}
}
