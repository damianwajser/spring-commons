package com.github.damianwajser.controllers;

import com.github.damianwajser.exceptions.impl.authentication.forbidden.ForbiddenException;
import com.github.damianwajser.exceptions.impl.authentication.forbidden.PermissionDeniedException;
import com.github.damianwajser.model.FooObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TestForbiddenController {

	@PostMapping("/forbbiden")
	private FooObject forbbiden() throws ForbiddenException {
		throw new ForbiddenException("", "forbbiden", Optional.empty());
	}

	@PostMapping("/permissionDenied")
	private FooObject permissionDenied() throws PermissionDeniedException {
		throw new PermissionDeniedException("", "permissionDenied", Optional.empty());
	}

}
