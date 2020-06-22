package com.github.damianwajser.model.enums.contry;

import com.github.damianwajser.validator.annotation.enums.Country_ISO3166;
import com.github.damianwajser.validator.constraint.enums.values.Countries;

public class CountryString {

	@Country_ISO3166(message = "some message", businessCode = "c-400")
	private String value;

	public CountryString() {
	}

	public CountryString(String value) {
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
