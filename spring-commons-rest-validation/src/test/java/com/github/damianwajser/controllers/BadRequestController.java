package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.model.global.NoEmptyObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BadRequestController {

	@PostMapping("/badrequest")
	private NoEmptyObject badRequest(@Valid NoEmptyObject obj) throws BadRequestException {
		return null;
	}
	@PutMapping("/badrequest")
	private NoEmptyObject badRequest_put(@Valid NoEmptyObject obj) throws BadRequestException {
		return null;
	}
}
