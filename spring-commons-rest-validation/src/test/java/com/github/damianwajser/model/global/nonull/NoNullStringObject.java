package com.github.damianwajser.model.global.nonull;

import com.github.damianwajser.validator.annotation.global.NotNull;

public class NoNullStringObject {

	@NotNull(businessCode = "a-400")
	private String value;

	public NoNullStringObject() {
	}

	public NoNullStringObject(String value) {
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
