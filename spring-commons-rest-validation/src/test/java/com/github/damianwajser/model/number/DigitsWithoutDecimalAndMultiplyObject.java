package com.github.damianwajser.model.number;

import com.github.damianwajser.validator.annotation.number.Digits;

import java.math.BigDecimal;

public class DigitsWithoutDecimalAndMultiplyObject {

	@Digits(integer = 4, fraction = 0, multipleOf = 10, businessCode = "a-400")
	private BigDecimal value;

	public DigitsWithoutDecimalAndMultiplyObject() {
	}

	public DigitsWithoutDecimalAndMultiplyObject(BigDecimal value) {
		super();
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(int BigDecimal) {
		this.value = value;
	}
}
