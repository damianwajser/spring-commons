package com.github.damianwajser.model.strings;

import com.github.damianwajser.validator.annotation.strings.Password;

public class PasswordStringObject {

	@Password(message = "error", businessCode = "a-400")
	private String value;

	public PasswordStringObject() {
	}

	public PasswordStringObject(String value) {
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
