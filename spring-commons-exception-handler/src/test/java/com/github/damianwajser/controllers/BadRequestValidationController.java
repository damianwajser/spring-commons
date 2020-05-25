package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.FooObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/validation")
public class BadRequestValidationController {

	@PostMapping("/badrequest")
	private FooObject badRequest(@Validated FooObject object) throws BadRequestException {
		throw new BadRequestException("2020", "badrequest", Optional.empty());
	}
}
