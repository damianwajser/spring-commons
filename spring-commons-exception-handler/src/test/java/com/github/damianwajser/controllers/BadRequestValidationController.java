package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.CustomValidationFooObject;
import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/validation")
public class BadRequestValidationController {

	@PostMapping("/badrequest")
	private FooObject badRequest(@Valid FooObject object) throws BadRequestException {
		throw new BadRequestException("400", "badrequest", Optional.empty());
	}
	@PostMapping("/custom")
	private FooObject badRequest(@Valid CustomValidationFooObject object) throws BadRequestException {
		throw new BadRequestException("400", "badrequest", Optional.empty());
	}
}
