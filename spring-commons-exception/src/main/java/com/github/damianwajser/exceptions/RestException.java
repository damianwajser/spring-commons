package com.github.damianwajser.exceptions;

import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class RestException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -5157622261482152458L;

	protected final List<ExceptionDetail> details;

	protected RestException(List<ExceptionDetail> details, Exception e) {
		super(details != null ? details.toString() : "", e);
		this.details = details;
	}

	public RestException(List<ExceptionDetail> details) {
		this(details, null);
	}

	public RestException(ExceptionDetail detail, Exception e) {
		this(Arrays.asList(detail), e);
	}

	public RestException(ExceptionDetail detail) {
		this(detail, null);
	}

	public RestException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		this(errorCode, errorMessage, errorDetail, null);
	}

	public RestException(String errorCode, String errorMessage, Optional<Object> errorDetail, Exception e) {
		this(new ExceptionDetail(errorCode, errorMessage, errorDetail), e);
	}

	public List<ExceptionDetail> getDetails() {
		return details;
	}

	public ErrorMessage getErrorMessage(HttpServletRequest request) {
		return new ErrorMessage(this.getDetails(), request);
	}

	public HttpStatus getHttpCode() {
		return this.getHttpCode(this.getClass());
	}

	private HttpStatus getHttpCode(Class<? extends RestException> ex) {
		ResponseStatus rs = ex.getAnnotation(ResponseStatus.class);
		if (rs != null) {
			return rs.code();
		} else {
			return getHttpCode((Class<? extends RestException>) ex.getSuperclass());
		}
	}

	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
		// always perform the default deserialization first
		aInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}
}
