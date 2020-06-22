package com.github.damianwajser.model.enums.contry;

import com.github.damianwajser.validator.annotation.enums.Country_ISO3166;
import com.github.damianwajser.validator.constraint.enums.values.Countries;

public class CountryEnum {

	@Country_ISO3166(message = "some message", businessCode = "c-400")
	private Countries value;

	public CountryEnum() {
	}

	public CountryEnum(Countries value) {
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
