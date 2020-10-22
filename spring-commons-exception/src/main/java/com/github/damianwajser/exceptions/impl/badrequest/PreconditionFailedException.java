package com.github.damianwajser.exceptions.impl.badrequest;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class PreconditionFailedException extends RestException {

	private static final long serialVersionUID = 4468401934359095440L;

	public PreconditionFailedException(ExceptionDetail detail) {
		super(detail);
	}

	public PreconditionFailedException(List<ExceptionDetail> details) {
		super(details);
	}

	public PreconditionFailedException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}

	public PreconditionFailedException(ExceptionDetail detail, Exception e) {
		super(detail, e);
	}

	public PreconditionFailedException(List<ExceptionDetail> details, Exception e) {
		super(details, e);
	}

	public PreconditionFailedException(String errorCode, String errorMessage, Optional<Object> errorDetail, Exception e) {
		super(errorCode, errorMessage, errorDetail, e);
	}
}
