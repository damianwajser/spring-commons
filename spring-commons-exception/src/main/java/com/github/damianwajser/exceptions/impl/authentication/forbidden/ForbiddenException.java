package com.github.damianwajser.exceptions.impl.authentication.forbidden;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RestException {

	/**
	 *
	 */
	private static final long serialVersionUID = 6530273083259853379L;

	public ForbiddenException(ExceptionDetail detail) {
		super(detail);
	}

	public ForbiddenException(List<ExceptionDetail> details) {
		super(details);
	}

	public ForbiddenException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}

}
