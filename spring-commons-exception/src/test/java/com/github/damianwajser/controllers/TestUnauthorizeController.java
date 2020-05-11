package com.github.damianwajser.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.damianwajser.exceptions.impl.authentication.unauthorize.AuthenticationFailedException;
import com.github.damianwajser.exceptions.impl.authentication.unauthorize.NotAuthenticatedException;
import com.github.damianwajser.exceptions.impl.authentication.unauthorize.UnauthorizeException;
import com.github.damianwajser.model.FooObject;

@RestController
public class TestUnauthorizeController {

	@PostMapping("/authenticationfailed")
	private FooObject authenticationFailed() throws AuthenticationFailedException {
		throw new AuthenticationFailedException("", "", Optional.empty());
	}

	@PostMapping("/notauthenticated")
	private FooObject permissionDenied() throws NotAuthenticatedException {
		throw new NotAuthenticatedException("", "", Optional.empty());
	}

	@PostMapping("/unauthorize")
	private FooObject unauthorize() throws UnauthorizeException {
		throw new UnauthorizeException("", "", Optional.empty());
	}
}
