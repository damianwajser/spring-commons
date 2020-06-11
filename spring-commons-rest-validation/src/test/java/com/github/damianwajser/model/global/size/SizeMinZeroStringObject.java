package com.github.damianwajser.model.global.size;

import com.github.damianwajser.validator.annotation.global.Size;

public class SizeMinZeroStringObject {

	@Size(max = 5, businessCode = "c-400")
	private String value;

	@Size(isNulleable = true, max = 5, businessCode = "c-400")
	private String nulleable;

	public SizeMinZeroStringObject() {
	}

	public SizeMinZeroStringObject(String value) {
		super();
		this.value = value;
		this.nulleable = value;
	}

	public SizeMinZeroStringObject(String value, String nulleable) {
		super();
		this.value = value;
		this.nulleable = nulleable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
