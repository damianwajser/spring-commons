package com.github.damianwajser.exceptions.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExceptionDetail implements Serializable {

	private static final long serialVersionUID = 1905128741950251207L;

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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
