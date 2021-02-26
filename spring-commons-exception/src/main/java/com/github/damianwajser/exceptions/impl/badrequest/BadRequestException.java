package com.github.damianwajser.exceptions.impl.badrequest;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RestException {

	private static final long serialVersionUID = -1861606846970257672L;

	public BadRequestException(ExceptionDetail detail) {
		super(detail);
	}

	public BadRequestException(List<ExceptionDetail> details) {
		super(details);
	}

	public BadRequestException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}

	public BadRequestException(ExceptionDetail detail, Exception e) {
		super(detail, e);
	}

	public BadRequestException(List<ExceptionDetail> details, Exception e) {
		super(details, e);
	}

	public BadRequestException(String errorCode, String errorMessage, Optional<Object> errorDetail, Exception e) {
		super(errorCode, errorMessage, errorDetail, e);
	}
}
