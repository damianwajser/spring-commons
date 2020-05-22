package com.github.damianwajser.model;

import org.springframework.http.HttpMethod;

import com.github.damianwajser.validator.annotation.NotEmpty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
