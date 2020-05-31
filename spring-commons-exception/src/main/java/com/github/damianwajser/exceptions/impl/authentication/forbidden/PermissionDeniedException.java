package com.github.damianwajser.exceptions.impl.authentication.forbidden;

import com.github.damianwajser.exceptions.model.ExceptionDetail;

import java.util.List;
import java.util.Optional;

public class PermissionDeniedException extends ForbiddenException {

	/**
	 *
	 */
	private static final long serialVersionUID = -261986049061018183L;

	public PermissionDeniedException(ExceptionDetail detail) {
		super(detail);
	}

	public PermissionDeniedException(List<ExceptionDetail> details) {
		super(details);
	}

	public PermissionDeniedException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		super(errorCode, errorMessage, errorDetail);
	}
}
