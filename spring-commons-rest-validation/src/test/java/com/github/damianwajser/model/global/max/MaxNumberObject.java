package com.github.damianwajser.model.global.max;

import com.github.damianwajser.validator.annotation.global.Max;

public class MaxNumberObject {

	@Max(max = 3, businessCode = "a-400")
	private Long value;

	public MaxNumberObject() {
	}

	public MaxNumberObject(Long value) {
		super();
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
