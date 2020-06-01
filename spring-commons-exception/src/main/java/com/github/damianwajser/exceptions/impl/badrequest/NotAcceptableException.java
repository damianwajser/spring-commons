package com.github.damianwajser.exceptions.impl.badrequest;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends RestException {

	private static final long serialVersionUID = -8012762310079534874L;

	public NotAcceptableException(ExceptionDetail detail) {
		super(detail);
	}

	public NotAcceptableException(List<ExceptionDetail> details) {
		super(details);
	}

	public NotAcceptableException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
