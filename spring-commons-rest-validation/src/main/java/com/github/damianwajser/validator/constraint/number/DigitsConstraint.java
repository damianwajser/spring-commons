package com.github.damianwajser.validator.constraint.number;

import com.github.damianwajser.validator.annotation.number.Digits;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class DigitsConstraint extends AbstractConstraint implements ConstraintValidator<Digits, Object> {

	private int maxIntegerLength;
	private int maxFractionLength;

	@Override
	public void initialize(Digits field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
		this.maxIntegerLength = field.integer();
		this.maxFractionLength = field.fraction();
		this.validateParameters();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		BigDecimal bigNum;
		if (field != null && field.getClass().isAssignableFrom(BigDecimal.class)) {
			bigNum = (BigDecimal) field;
		} else if (field != null) {
			bigNum = (new BigDecimal(field.toString())).stripTrailingZeros();
		} else {
			return true;
		}

		int integerPartLength = bigNum.precision() - bigNum.scale();
		int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();
		return this.maxIntegerLength < integerPartLength || this.maxFractionLength < fractionPartLength;

	}

	private void validateParameters() {
		if (this.maxIntegerLength < 0) {
			throw new IllegalArgumentException("The length of the integer part cannot be negative.");
		} else if (this.maxFractionLength < 0) {
			throw new IllegalArgumentException("The length of the fraction part cannot be negative.");
		}
	}
}
