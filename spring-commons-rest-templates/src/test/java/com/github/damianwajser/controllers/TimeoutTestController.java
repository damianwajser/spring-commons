package com.github.damianwajser.controllers;

import com.github.damianwajser.model.snake.SingletonObject;
import com.github.damianwajser.model.timeout.TimeOutObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class TimeoutTestController {

	@PostMapping("/timeout")
	public TimeOutObject timeout(@RequestBody TimeOutObject request) throws InterruptedException {
		System.out.println("start delay: " + (new Date().getTime() - request.getStart()));
		Thread.sleep(400);
		System.out.println("stop delay: " + (new Date().getTime() - request.getStart()));
		SingletonObject.getInstance().increment();
		System.out.println("increment: " + (new Date().getTime() - request.getStart()));
		request.setResult(SingletonObject.getInstance().getValue());
		request.setEnd(new Date().getTime());
		return request;
	}

	@GetMapping("/without_timeout")
	private Object without_timeout() throws InterruptedException {
		System.out.println("call without timeout");
		return SingletonObject.getInstance().getValue();
	}
}
