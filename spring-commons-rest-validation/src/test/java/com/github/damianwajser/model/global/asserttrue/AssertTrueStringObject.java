package com.github.damianwajser.model.global.asserttrue;

import com.github.damianwajser.validator.annotation.global.AssertTrue;

public class AssertTrueStringObject {

	@AssertTrue(businessCode = "a-400")
	private String value;

	public AssertTrueStringObject() {
		value = null;
	}

	public AssertTrueStringObject(String value) {
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
