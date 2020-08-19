package com.github.damianwajser.model.strings;

import com.github.damianwajser.validator.annotation.strings.UUID;

public class UUIDObject {

	@UUID(message = "error", businessCode = "a-400")
	private String value;

	public UUIDObject() {
	}

	public UUIDObject(String value) {
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
