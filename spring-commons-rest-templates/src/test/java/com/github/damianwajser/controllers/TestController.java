package com.github.damianwajser.controllers;

import com.github.damianwajser.model.snake.RequestToController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	@Qualifier("snake_template")
	@Autowired
	private RestTemplate restTemplateSnake;

	@PatchMapping("/patch_test")
	private RequestToController test_patch() {
		RequestToController obj = new RequestToController();
		obj.setSomeValue("patch");
		return obj;
	}

	@GetMapping("/replayheaders")
	private Object test1() {
		return restTemplate.getForObject("https://httpbin.org/get", Object.class);
	}

	@PostMapping("/snake_case")
	private Object snake_case(@RequestBody RequestToController request) {
		return restTemplateSnake.postForObject("https://httpbin.org/post", request, Object.class);
	}

	@PostMapping("/camel_case")
	private Object camel_case(@RequestBody RequestToController request) {
		return restTemplate.postForObject("https://httpbin.org/post", request, Object.class);
	}

	@PostMapping("/snake_case/local_date")
	private String snake_caseWithResponse(@RequestBody RequestToController request) {
		Map<String, String> mapResponse = restTemplateSnake.postForObject("https://httpbin.org/post", request, Map.class);
		return mapResponse.get("data");
	}

}
