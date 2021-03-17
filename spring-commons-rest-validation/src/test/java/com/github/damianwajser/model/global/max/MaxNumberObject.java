package com.github.damianwajser.model.global.max;

@Deprecated
public class MaxNumberObject {

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
