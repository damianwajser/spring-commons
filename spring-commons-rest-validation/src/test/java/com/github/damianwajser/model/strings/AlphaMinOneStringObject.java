package com.github.damianwajser.model.strings;

import com.github.damianwajser.validator.annotation.global.Size;
import com.github.damianwajser.validator.annotation.strings.AlphaNumeric;

public class AlphaMinOneStringObject {

	@AlphaNumeric(message = "somemmessage", min = 1, max = 5, businessCode = "c-400", allowSpaces = false)
	private String value;

	public AlphaMinOneStringObject() {
	}

	public AlphaMinOneStringObject(String value) {
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
