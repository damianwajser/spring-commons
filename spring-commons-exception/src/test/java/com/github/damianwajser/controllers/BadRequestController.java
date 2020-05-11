package com.github.damianwajser.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.FooObject;

@RestController
public class BadRequestController {

	@PostMapping("/badrequest")
	private FooObject badRequest() throws BadRequestException {
		throw new BadRequestException("", "", Optional.empty());
	}
}
