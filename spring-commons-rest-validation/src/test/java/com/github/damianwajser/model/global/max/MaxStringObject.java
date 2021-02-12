package com.github.damianwajser.model.global.max;

@Deprecated
public class MaxStringObject {

	private String value;
	private String nulleable;

	public MaxStringObject() {
	}

	public MaxStringObject(String value) {
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
