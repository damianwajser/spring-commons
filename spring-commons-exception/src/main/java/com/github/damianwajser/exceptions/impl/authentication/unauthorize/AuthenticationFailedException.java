package com.github.damianwajser.exceptions.impl.authentication.unauthorize;

import java.util.List;
import java.util.Optional;

import com.github.damianwajser.exceptions.model.ExceptionDetail;

public class AuthenticationFailedException extends UnauthorizeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3193488927248995976L;

	public AuthenticationFailedException(ExceptionDetail detail) {
		super(detail);
	}

	public AuthenticationFailedException(List<ExceptionDetail> details) {
		super(details);
	}

	public AuthenticationFailedException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
