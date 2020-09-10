package com.github.damianwajser.model.global.min;

import com.github.damianwajser.validator.annotation.global.Min;

public class MinNumberObject {

	@Min(value = 3, businessCode = "a-400")
	private Long value;

	public MinNumberObject() {
	}

	public MinNumberObject(Long value) {
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
