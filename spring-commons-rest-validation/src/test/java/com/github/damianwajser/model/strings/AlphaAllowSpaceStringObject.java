package com.github.damianwajser.model.strings;

import com.github.damianwajser.validator.annotation.strings.AlphaNumeric;

public class AlphaAllowSpaceStringObject {

	@AlphaNumeric(message = "somemmessage", min = 1, max = 5, businessCode = "c-400")
	@AlphaNumeric(message = "somemmessage", min = 1, max = 6, businessCode = "c-400")
	private String value;

	public AlphaAllowSpaceStringObject() {
	}

	public AlphaAllowSpaceStringObject(String value) {
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
