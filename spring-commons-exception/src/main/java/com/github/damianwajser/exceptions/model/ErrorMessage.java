package com.github.damianwajser.exceptions.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public class ErrorMessage {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessage.class);

	@JsonProperty("details")
	private List<ExceptionDetail> details;
	private String timestamp;
	@JsonProperty("path")
	private String path;

	public ErrorMessage(List<ExceptionDetail> details, HttpServletRequest request) {
		this(details, Encode.forJava(request.getRequestURI()));
	}

	@JsonCreator
	public ErrorMessage(@JsonProperty("details") List<ExceptionDetail> details, @JsonProperty("path") String path) {
		this.details = details;
		this.path = path;
		this.timestamp = LocalDateTime.now().toString();
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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
