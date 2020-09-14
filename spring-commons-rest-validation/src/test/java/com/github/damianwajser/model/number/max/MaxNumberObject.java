package com.github.damianwajser.model.number.max;


import com.github.damianwajser.validator.annotation.number.Max;

public class MaxNumberObject {

	@Max(value = 3, businessCode = "a-400")
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
