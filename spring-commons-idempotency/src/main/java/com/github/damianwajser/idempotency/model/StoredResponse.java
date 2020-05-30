package com.github.damianwajser.idempotency.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StoredResponse implements Serializable {

	private String body;
	private final Map<String, String> headers;
	private int statusCode;
	private boolean lock;

	public StoredResponse() {
		this(null, null, HttpStatus.OK.value(), true);
	}

	public StoredResponse(String body, Map<String, String> headers, int statusCode) {
		this(body, headers, statusCode, true);
	}

	public StoredResponse(String body, Map<String, String> headers, int statusCode, boolean lock) {
		this.body = body;
		this.headers = headers == null ? new HashMap<>() : headers;
		this.statusCode = statusCode;
		this.lock = lock;
	}

	public String getBody() {
		return body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public boolean isLocked() {
		return lock;
	}

	public void lock() {
		lock = true;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public void unLock() {
		lock = false;
	}
}
