package com.github.damianwajser.model.global.onenotnull;

import com.github.damianwajser.validator.annotation.global.OneNotNull;

@OneNotNull(
		fields = {"value","value2"},
		businessCode = ""

)
public class OneNotNullObject {

	private Object value;

	private Object value2;

	public OneNotNullObject() {
	}

	public OneNotNullObject(Object value, Object value2) {
		this.value = value;
		this.value2 = value2;
	}

	public Object getValue2() {
		return value2;
	}

	public void setValue2(Object value2) {
		this.value2 = value2;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
