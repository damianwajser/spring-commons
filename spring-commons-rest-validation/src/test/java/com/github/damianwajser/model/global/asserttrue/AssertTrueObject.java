package com.github.damianwajser.model.global.asserttrue;

import com.github.damianwajser.validator.annotation.global.AssertTrue;

public class AssertTrueObject {

	@AssertTrue(businessCode = "a-400")
	private Boolean value;

	public AssertTrueObject() {
		value = null;
	}

	public AssertTrueObject(Boolean value) {
		super();
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}
}
