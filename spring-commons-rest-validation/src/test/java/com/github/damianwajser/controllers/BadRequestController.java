package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BadRequestController {

	@PostMapping("/badrequest")
	private FooObject badRequest(@Valid FooObject foo) throws BadRequestException {
		return foo;
	}

	@PutMapping("/badrequest")
	private FooObject badRequest_put(@Valid FooObject foo) throws BadRequestException {
		return foo;
	}
}
