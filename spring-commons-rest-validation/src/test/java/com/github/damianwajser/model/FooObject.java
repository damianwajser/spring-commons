package com.github.damianwajser.model;

import com.github.damianwajser.validator.annotation.NotEmpty;
import org.springframework.http.HttpMethod;

public class FooObject {

	@NotEmpty(excludes = HttpMethod.PUT, businessCode = "a-400")
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
