package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.validator.CustomValidator;
import com.github.damianwajser.model.FooObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom_validator")
public class BadRequestCustomValidatorController {

	private final CustomValidator validator;

	@Autowired
	public BadRequestCustomValidatorController(CustomValidator validator) {
		this.validator = validator;
	}

	@PostMapping("/custom")
	private FooObject badRequest(@RequestBody FooObject object) {
		validator.validate( object );
		return new FooObject();
	}

}
