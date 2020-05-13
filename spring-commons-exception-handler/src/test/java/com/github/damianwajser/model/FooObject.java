package com.github.damianwajser.model;

import javax.validation.constraints.NotEmpty;

public class FooObject {

	@NotEmpty
	private String value;

	public FooObject() {
	}

	public FooObject(String value) {
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
