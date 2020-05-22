package com.github.damianwajser.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.damianwajser.exceptions.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

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

	public ErrorMessage getErrorMessage(HttpServletRequest request){
		return new ErrorMessage(this.getDetails(), request);
	}

	public HttpStatus getHttpCode(){
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
}
