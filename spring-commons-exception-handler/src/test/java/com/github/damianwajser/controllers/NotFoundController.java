package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.notfound.NotFoundException;
import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class NotFoundController {

	@PostMapping("/notfound")
	private FooObject badRequest() throws NotFoundException {
		throw new NotFoundException("202", "notfound", Optional.empty());
	}
}
