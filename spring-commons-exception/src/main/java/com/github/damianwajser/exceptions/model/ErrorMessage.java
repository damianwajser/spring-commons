package com.github.damianwajser.exceptions.model;

import java.util.Date;
import java.util.List;

import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

public class ErrorMessage {

	private static Logger LOGGER = LoggerFactory.getLogger(ErrorMessage.class);

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
		//return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return request.getRequestURI();
	}

	@Override
	public String toString() {
		return "ErrorMessage{" +
				"details=" + details +
				", timestamp='" + timestamp + '\'' +
				", path='" + path + '\'' +
				'}';
	}
}
