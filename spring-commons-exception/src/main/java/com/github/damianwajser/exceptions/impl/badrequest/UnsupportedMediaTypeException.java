package com.github.damianwajser.exceptions.impl.badrequest;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedMediaTypeException extends RestException {

	private static final long serialVersionUID = 3668070151764793050L;

	public UnsupportedMediaTypeException(ExceptionDetail detail) {
		super(detail);
	}

	public UnsupportedMediaTypeException(List<ExceptionDetail> details) {
		super(details);
	}

	public UnsupportedMediaTypeException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}

	public UnsupportedMediaTypeException(ExceptionDetail detail, Exception e) {
		super(detail, e);
	}

	public UnsupportedMediaTypeException(List<ExceptionDetail> details, Exception e) {
		super(details, e);
	}

	public UnsupportedMediaTypeException(String errorCode, String errorMessage, Optional<Object> errorDetail, Exception e) {
		super(errorCode, errorMessage, errorDetail, e);
	}
}
