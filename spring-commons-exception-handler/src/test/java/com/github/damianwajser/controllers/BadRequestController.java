package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.FooObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
public class BadRequestController {

	@PostMapping("/badrequest")
	private FooObject badRequest() throws BadRequestException {
		throw new BadRequestException("400", "badrequest", Optional.empty());
	}

	@GetMapping("/badrequest/{age}")
	public Object badRequestWithPath(@PathVariable @Valid Integer age) {
		return String.format("The number is %s", age);
	}
}
