package com.github.damianwajser.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.damianwajser.exceptions.impl.notfound.NotFoundException;
import com.github.damianwajser.model.FooObject;

@RestController
public class NotFoundController {

	@PostMapping("/notfound")
	private FooObject badRequest() throws NotFoundException {
		throw new NotFoundException("", "", Optional.empty());
	}
}
