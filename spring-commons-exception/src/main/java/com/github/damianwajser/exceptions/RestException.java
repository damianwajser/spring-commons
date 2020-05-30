package com.github.damianwajser.exceptions;

import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
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

	public RestException(List<ExceptionDetail> details) {
		Assert.notNull(details, "details can't be null");
		this.details = details;
	}

	public RestException(ExceptionDetail detail) {
		this(Arrays.asList(detail));
		Assert.notNull(detail, "detail can't be null");
	}

	public RestException(String errorCode, String errorMessage, Optional<Object> errorDetail) {
		this(new ExceptionDetail(errorCode, errorMessage, errorDetail));
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

	/**
	 * Always treat deserialization as a full-blown constructor, by validating
	 * the final state of the de-serialized object.
	 */
	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
		// always perform the default deserialization first
		aInputStream.defaultReadObject();
	}

	/**
	 * This is the default implementation of writeObject. Customise if
	 * necessary.
	 */
	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}
}
