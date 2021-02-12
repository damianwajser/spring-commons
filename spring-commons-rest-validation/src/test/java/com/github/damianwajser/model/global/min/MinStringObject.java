package com.github.damianwajser.model.global.min;

@Deprecated
public class MinStringObject {

	private String value;
	private String nulleable;

	public MinStringObject() {
	}

	public MinStringObject(String value) {
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
