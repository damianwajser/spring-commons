package com.github.damianwajser.exceptions.impl.unsupportedmediatype;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsuportedMediaTypeException extends RestException {	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3753742475125448784L;

	public UnsuportedMediaTypeException(ExceptionDetail detail) {
		super(detail);
	}

	public UnsuportedMediaTypeException(List<ExceptionDetail> details) {
		super(details);
	}

	public UnsuportedMediaTypeException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
