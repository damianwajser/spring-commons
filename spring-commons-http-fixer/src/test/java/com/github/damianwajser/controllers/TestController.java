package com.github.damianwajser.controllers;

import com.github.damianwajser.model.FooObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

	@PostMapping("/postok")
	private FooObject postok() {
		return new FooObject("something");
	}

	@PostMapping("/postnok")
	private Object postnok() {
		throw new RuntimeException("error");
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/postchanged")
	private Object postchanged() {
		return new FooObject("something");
	}

	@GetMapping("/getok")
	private Object getok() {
		return new FooObject("something");
	}

	@GetMapping("/getnok")
	private Object getnok() {
		throw new RuntimeException("error");
	}

	@DeleteMapping("/delete_void")
	private void getvoid() {
	}

}
