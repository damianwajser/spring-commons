package com.github.damianwajser.controllers;

import com.github.damianwajser.model.especifics.NoEmptyObjectOnlyPut;
import com.github.damianwajser.model.global.NoEmptyObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BadRequestController {

	@PostMapping("/badrequest")
	private NoEmptyObject badRequest(@Valid NoEmptyObject obj) {
		return null;
	}

	@PutMapping("/badrequest")
	private NoEmptyObject badRequest_put(@Valid NoEmptyObject obj) {
		return null;
	}

	@PutMapping("/badrequest_only_put")
	private NoEmptyObjectOnlyPut badRequest_put(@Valid NoEmptyObjectOnlyPut obj) {
		return null;
	}

	@PostMapping("/badrequest_only_put")
	private NoEmptyObjectOnlyPut badRequest_post(@Valid NoEmptyObjectOnlyPut obj) {
		return null;
	}
}
