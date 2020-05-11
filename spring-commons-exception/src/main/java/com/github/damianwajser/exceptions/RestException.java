package com.github.damianwajser.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.Assert;

import com.github.damianwajser.exceptions.model.ExceptionDetail;

abstract public class RestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5157622261482152458L;

	protected List<ExceptionDetail> details;

	public RestException(List<ExceptionDetail> details) {
		Assert.notNull(details, "details can't be null");
		this.details = details;
	}

	public RestException(ExceptionDetail detail) {
		Assert.notNull(detail, "detail can't be null");
		if (this.details == null) {
			this.details = new ArrayList<>();
		}
		this.details.add(detail);
	}

	public RestException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		this(new ExceptionDetail(errorCode, errorMessage, errorDetail));
	}

	public List<ExceptionDetail> getDetails() {
		return details;
	}
}
