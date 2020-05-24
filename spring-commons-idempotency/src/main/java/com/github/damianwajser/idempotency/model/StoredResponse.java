package com.github.damianwajser.idempotency.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StoredResponse implements Serializable {

	private String body;
	private Map<String, String> headers;
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

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public boolean isLocked() {
		return lock;
	}

	public void lock() {
		lock = true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof StoredResponse)) return false;
		StoredResponse that = (StoredResponse) o;
		return getStatusCode() == that.getStatusCode() &&
				lock == that.lock &&
				Objects.equals(getBody(), that.getBody()) &&
				Objects.equals(getHeaders(), that.getHeaders());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getBody(), getHeaders(), getStatusCode(), lock);
	}

	@Override
	public String toString() {
		return "StoredResponse{" +
				"body='" + body + '\'' +
				", headers=" + headers +
				", statusCode=" + statusCode +
				", lock=" + lock +
				'}';
	}

	public void unLock() {
		lock = false;
	}
}
