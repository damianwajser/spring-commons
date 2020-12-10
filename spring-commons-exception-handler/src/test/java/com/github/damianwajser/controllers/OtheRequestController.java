package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.FooObject;
import com.github.damianwajser.validator.annotation.global.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
public class OtheRequestController {

	@PostMapping("/other")
	private FooObject badRequest() throws BadRequestException {
		throw new NullPointerException();
	}

}
