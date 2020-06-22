package com.github.damianwajser.model.enums.global;

import com.github.damianwajser.validator.annotation.enums.MatchEnum;
import com.github.damianwajser.validator.constraint.enums.values.Countries;

public class MatchString {

	@MatchEnum(enumClass = Countries.class, message = "some message", businessCode = "c-400")
	private String value;

	public MatchString() {
	}

	public MatchString(String value) {
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
