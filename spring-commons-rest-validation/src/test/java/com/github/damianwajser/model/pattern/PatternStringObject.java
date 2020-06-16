package com.github.damianwajser.model.pattern;

import com.github.damianwajser.validator.annotation.global.Pattern;

public class PatternStringObject {

	@Pattern(businessCode = "a-400", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
	private String value;

	public PatternStringObject() {
	}

	public PatternStringObject(String value) {
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
