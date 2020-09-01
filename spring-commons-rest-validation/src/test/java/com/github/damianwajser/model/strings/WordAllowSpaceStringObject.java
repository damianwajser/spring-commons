package com.github.damianwajser.model.strings;

import com.github.damianwajser.validator.annotation.strings.Word;

public class WordAllowSpaceStringObject {

	@Word(message = "somemmessage", min = 1, max = 6, businessCode = "c-400")
	private String value;

	public WordAllowSpaceStringObject() {
	}

	public WordAllowSpaceStringObject(String value) {
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
