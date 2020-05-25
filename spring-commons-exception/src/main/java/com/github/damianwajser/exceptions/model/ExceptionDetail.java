package com.github.damianwajser.exceptions.model;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExceptionDetail {

	private final String errorCode;
	private final Optional<Object> errorDetail;
	private final String errorMessage;
	private Map<String, Object> metaData;

	public ExceptionDetail(String errorCode, String errorMessage, Optional<Object> detail) {
		Assert.notNull(errorCode, "errorCode dont be null");
		Assert.notNull(errorMessage, "errorMessage dont be null");
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDetail = detail;
		this.metaData = new HashMap<>();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setMetaData(String key, Object value) {
		metaData.put(key, value);
	}

	public Map<String, Object> getMetaData() {
		return this.metaData;
	}

	public Optional<Object> getErrorDetail() {
		return errorDetail;
	}
}
