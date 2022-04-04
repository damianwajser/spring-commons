package com.github.damianwajser.model.number;

import com.github.damianwajser.validator.annotation.number.Digits;

public class DigitsWithoutDecimalAndMultiplyObjectInt {

	@Digits(integer = 4, fraction = 0, multipleOf = 10, businessCode = "a-400")
	private int value;

	public DigitsWithoutDecimalAndMultiplyObjectInt() {
	}

	public DigitsWithoutDecimalAndMultiplyObjectInt(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
