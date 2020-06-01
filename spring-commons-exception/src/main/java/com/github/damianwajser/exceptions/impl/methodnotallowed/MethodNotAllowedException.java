package com.github.damianwajser.exceptions.impl.methodnotallowed;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RestException {

	private static final long serialVersionUID = -8012762310079534874L;

	public MethodNotAllowedException(ExceptionDetail detail) {
		super(detail);
	}

	public MethodNotAllowedException(List<ExceptionDetail> details) {
		super(details);
	}

	public MethodNotAllowedException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
