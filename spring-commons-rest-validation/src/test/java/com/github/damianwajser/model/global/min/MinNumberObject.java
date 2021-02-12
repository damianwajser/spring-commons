package com.github.damianwajser.model.global.min;

@Deprecated
public class MinNumberObject {

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
