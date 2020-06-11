package com.github.damianwajser.model.global;


import com.github.damianwajser.validator.annotation.global.Past;

public class PastAuxObject {

	@Past(businessCode = "asd")
	private Object value;
	@Past(businessCode = "asd", isNulleable = true)
	private Object nulleable;

	public PastAuxObject() {
	}

	public PastAuxObject(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
