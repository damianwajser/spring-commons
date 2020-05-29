package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.FooObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class IdempotencyController {

	public static String value = "match";

	@Cacheable
	@PostMapping("/idempotency")
	private FooObject test_post() {
		return new FooObject(value);
	}

	@PostMapping("/idempotency_bad_request")
	private FooObject test_post_bad() throws BadRequestException {
		throw new BadRequestException("asd", "asd", Optional.empty());
	}

	@PostMapping("/idempotency_delay")
	private FooObject test_post_delay() throws InterruptedException {
		Thread.sleep(1500l);
		return new FooObject(value);
	}
}
