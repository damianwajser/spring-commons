package com.github.damianwajser.exceptions.impl.authentication.unauthorize;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorize")
public class UnauthorizeException extends RestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4243632841596140730L;

	public UnauthorizeException(ExceptionDetail detail) {
		super(detail);
	}

	public UnauthorizeException(List<ExceptionDetail> details) {
		super(details);
	}

	public UnauthorizeException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
