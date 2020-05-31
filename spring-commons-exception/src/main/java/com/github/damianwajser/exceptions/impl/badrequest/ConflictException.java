package com.github.damianwajser.exceptions.impl.badrequest;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends RestException {

	/**
	 *
	 */
	private static final long serialVersionUID = 3193488927248995976L;

	public ConflictException(ExceptionDetail detail) {
		super(detail);
	}

	public ConflictException(List<ExceptionDetail> details) {
		super(details);
	}

	public ConflictException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
