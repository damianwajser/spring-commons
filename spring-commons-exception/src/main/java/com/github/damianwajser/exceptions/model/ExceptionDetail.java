package com.github.damianwajser.exceptions.model;

import java.util.Optional;

import org.springframework.util.Assert;

public class ExceptionDetail {

	private final String errorCode;
	private final Optional<Object> errorDetail;
	private final String errorMessage;

	public ExceptionDetail(String errorCode, String errorMessage, Optional<Object> detail) {
		Assert.notNull(errorCode, "errorCode dont be null");
		Assert.notNull(errorMessage, "errorMessage dont be null");
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDetail = detail;

	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Optional<Object> getErrorDetail() {
		return errorDetail;
	}
}
