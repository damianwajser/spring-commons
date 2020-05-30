package com.github.damianwajser.exceptions.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public class ErrorMessage {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessage.class);

	private List<ExceptionDetail> details;
	private String timestamp;
	private String path;

	public ErrorMessage(List<ExceptionDetail> details, HttpServletRequest request) {
		this.details = details;
		this.path = this.getPath(request);
		this.timestamp = new Date().toString();
		LOGGER.debug("Create Error Message: {}", this);
	}

	public List<ExceptionDetail> getDetails() {
		return details;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getPath() {
		return path;
	}

	private String getPath(HttpServletRequest request) {
		return Encode.forJava(request.getRequestURI());
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
