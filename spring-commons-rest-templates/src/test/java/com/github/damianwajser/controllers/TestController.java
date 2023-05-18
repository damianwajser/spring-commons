package com.github.damianwajser.controllers;

import com.github.damianwajser.model.snake.RequestToController;
import com.github.damianwajser.tests.AbstractHttpTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
		return restTemplate.getForObject(AbstractHttpTest.getEchoUrl() + "/get", Object.class);
	}

	@PostMapping("/snake_case")
	private Object snake_case(@RequestBody RequestToController request) {
		return restTemplateSnake.postForObject(AbstractHttpTest.getEchoUrl() + "/post", request, Object.class);
	}

	@PostMapping("/camel_case")
	private Object camel_case(@RequestBody RequestToController request) {
		return restTemplate.postForObject(AbstractHttpTest.getEchoUrl() + "/post", request, Object.class);
	}


	@GetMapping("/replayheaders_add_4")
	private Object add() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Client-Id", "4");
		//le intento agregar el id 4 que ya estaba agregado
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		return this.restTemplate
				.exchange(AbstractHttpTest.getEchoUrl() + "/get", HttpMethod.GET, entity, Map.class).getBody();
	}
}
