package com.github.damianwajser.model.especifics;

import com.github.damianwajser.validator.annotation.global.NotEmpty;

import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.PUT;

public class NoEmptyObjectOnlyPut {

	@NotEmpty(onlyIn = PUT, businessCode = "a-400", excludes = {PATCH, PUT})
	private String value;

	public NoEmptyObjectOnlyPut() {
	}

	public NoEmptyObjectOnlyPut(String value) {
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
