package com.github.damianwajser.model.global.max;

import com.github.damianwajser.validator.annotation.global.Max;
@Deprecated
public class MaxPrimitiveObject {

	@Max(max = 2, businessCode = "a-400")
	private int value;

	public MaxPrimitiveObject() {
	}

	public MaxPrimitiveObject(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
