package com.github.damianwajser.model.number.max;


import com.github.damianwajser.validator.annotation.number.Max;

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
