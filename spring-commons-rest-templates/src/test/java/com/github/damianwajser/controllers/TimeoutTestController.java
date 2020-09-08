package com.github.damianwajser.controllers;

import com.github.damianwajser.model.snake.SingletonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeoutTestController {

	@GetMapping("/timeout")
	private Object timeout() throws InterruptedException {
		System.out.println("start delay");
		Thread.sleep(40);
		System.out.println("stop delay");
		SingletonObject.getInstance().increment();
		System.out.println("increment");
		return SingletonObject.getInstance().getValue();
	}

	@GetMapping("/without_timeout")
	private Object without_timeout() throws InterruptedException {
		System.out.println("call without timeout");
		return SingletonObject.getInstance().getValue();
	}
}
