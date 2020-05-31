package com.github.damianwajser.model.global.email;

import com.github.damianwajser.validator.annotation.global.Email;

public class EmailStringObject {

	@Email(businessCode = "a-400")
	private String value;

	public EmailStringObject() {
	}

	public EmailStringObject(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
