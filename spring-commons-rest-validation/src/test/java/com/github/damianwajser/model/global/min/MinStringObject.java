package com.github.damianwajser.model.global.min;

import com.github.damianwajser.validator.annotation.global.Min;

public class MinStringObject {

	@Min(value = 2, businessCode = "a-400")
	private String value;
	@Min(value = 2, businessCode = "a-400", isNulleable = true)
	private String nulleable;

	public MinStringObject() {
	}

	public MinStringObject(String value) {
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
