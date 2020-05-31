package com.github.damianwajser.exceptions.impl.notfound;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RestException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2527808074583211325L;

	public NotFoundException(ExceptionDetail detail) {
		super(detail);
	}

	public NotFoundException(List<ExceptionDetail> details) {
		super(details);
	}

	public NotFoundException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
