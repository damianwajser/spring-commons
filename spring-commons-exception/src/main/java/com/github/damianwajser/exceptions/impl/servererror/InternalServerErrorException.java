package com.github.damianwajser.exceptions.impl.servererror;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RestException {

	private static final long serialVersionUID = -795969938463457861L;

	public InternalServerErrorException(ExceptionDetail detail) {
		super(detail);
	}

	public InternalServerErrorException(List<ExceptionDetail> details) {
		super(details);
	}

	public InternalServerErrorException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}

	public InternalServerErrorException(ExceptionDetail detail, Exception e) {
		super(detail, e);
	}

	public InternalServerErrorException(List<ExceptionDetail> details, Exception e) {
		super(details, e);
	}

	public InternalServerErrorException(String errorCode, String errorMessage, Optional<Object> errorDetail, Exception e) {
		super(errorCode, errorMessage, errorDetail, e);
	}
}
