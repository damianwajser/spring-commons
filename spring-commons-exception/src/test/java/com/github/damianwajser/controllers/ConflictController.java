package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.ConflictException;
import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ConflictController {

	@PostMapping("/conflict")
	private FooObject conflict() throws ConflictException {
		throw new ConflictException("2020", "conflict", Optional.empty());
	}
}
