package com.github.damianwajser.model;

import com.github.damianwajser.validator.annotation.global.NotEmpty;

public class CustomValidationFooObject {

	@NotEmpty(businessCode = "defaultCode")
	private String defaultMessage;
	@NotEmpty(businessCode = "customCode", message = "{spring.commons.validation.constraints.NotEmpty.message}")
	private String cusotmMessage;

	@NotEmpty(businessCode = "noStringMessage", message = "{spring.commons}")
	private String noStringMessage;

	@NotEmpty(businessCode = "customStringCode", message = "message")
	private String cusotmStringMessage;

	public String getNoStringMessage() {
		return noStringMessage;
	}

	public void setNoStringMessage(String noStringMessage) {
		this.noStringMessage = noStringMessage;
	}

	public String getCusotmStringMessage() {
		return cusotmStringMessage;
	}

	public void setCusotmStringMessage(String cusotmStringMessage) {
		this.cusotmStringMessage = cusotmStringMessage;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public String getCusotmMessage() {
		return cusotmMessage;
	}

	public void setCusotmMessage(String cusotmMessage) {
		this.cusotmMessage = cusotmMessage;
	}

	public CustomValidationFooObject() {
	}

	public CustomValidationFooObject(String defaultMessage, String cusotmMessage) {
		super();
		this.defaultMessage = defaultMessage;
		this.cusotmMessage = cusotmMessage;
	}

}
