package com.github.damianwajser.model.enums.global;

import com.github.damianwajser.validator.annotation.enums.MatchEnum;
import com.github.damianwajser.validator.constraint.enums.values.Countries;

public class MatchEnumObject {

	@MatchEnum(enumClass = Countries.class, message = "some message", businessCode = "c-400")
	private Countries value;

	public MatchEnumObject() {
	}

	public MatchEnumObject(Countries value) {
		super();
		this.value = value;
	}

	public Countries getValue() {
		return value;
	}

	public void setValue(Countries value) {
		this.value = value;
	}
}
