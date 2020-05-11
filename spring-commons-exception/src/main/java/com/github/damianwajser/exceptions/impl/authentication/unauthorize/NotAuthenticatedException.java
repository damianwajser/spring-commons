package com.github.damianwajser.exceptions.impl.authentication.unauthorize;

import java.util.List;
import java.util.Optional;

import com.github.damianwajser.exceptions.model.ExceptionDetail;

public class NotAuthenticatedException extends UnauthorizeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7490860665321345666L;

	public NotAuthenticatedException(ExceptionDetail detail) {
		super(detail);
	}

	public NotAuthenticatedException(List<ExceptionDetail> details) {
		super(details);
	}

	public NotAuthenticatedException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
