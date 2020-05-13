package com.github.damianwajser.exceptions.handlers.model;

import java.util.Date;
import java.util.List;

import com.github.damianwajser.exceptions.model.ExceptionDetail;

public class ErrorMessage {

	private List<ExceptionDetail> details;
	private String timestamp;
	private String path;

	public ErrorMessage(List<ExceptionDetail> details, String path) {
		this.details = details;
		this.path = path;
		this.timestamp = new Date().toString();
	}

	public List<ExceptionDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ExceptionDetail> details) {
		this.details = details;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
