package com.github.damianwajser.model.strings;

import com.github.damianwajser.validator.annotation.strings.Word;

public class WordMinOneStringObject {

	@Word(message = "somemmessage", min = 1, max = 5, businessCode = "c-400", allowSpaces = false)
	private String value;
	@Word(message = "somemmessage", min = 1, max = 5, businessCode = "c-400", allowSpaces = false, isNulleable = true)
	private String nulleable;

	public WordMinOneStringObject() {
	}

	public WordMinOneStringObject(String value) {
		super();
		this.value = value;
		this.nulleable = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
