package com.github.damianwajser.model.global.size;

import com.github.damianwajser.validator.annotation.global.Size;

public class SizeMinZeroStringObject {

	@Size(max = 5, businessCode = "c-400")
	private String value;

	public SizeMinZeroStringObject() {
	}

	public SizeMinZeroStringObject(String value) {
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
