package com.github.damianwajser.model;

import com.github.damianwajser.validator.annotation.cards.CardToken;
import com.github.damianwajser.validator.annotation.global.NotEmpty;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Collection;

public class NoEmptyObject {

	@NotEmpty(excludes = HttpMethod.PUT, businessCode = "a-400")
	private String value;

	public NoEmptyObject() {
	}

	public NoEmptyObject(String value) {
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
