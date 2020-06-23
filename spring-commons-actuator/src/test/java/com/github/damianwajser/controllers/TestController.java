package com.github.damianwajser.controllers;

import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@PostMapping("/postok")
	private FooObject postok() {
		return new FooObject("something");
	}
}
