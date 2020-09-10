package com.github.damianwajser.model.global.max;

import com.github.damianwajser.validator.annotation.global.Max;

public class MaxStringObject {

	@Max(value = 2, businessCode = "a-400")
	private String value;
	@Max(value = 2, businessCode = "a-400", isNulleable = true)
	private String nulleable;

	public MaxStringObject() {
	}

	public MaxStringObject(String value) {
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
