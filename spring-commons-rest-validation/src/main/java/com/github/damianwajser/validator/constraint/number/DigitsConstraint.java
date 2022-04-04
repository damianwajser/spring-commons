package com.github.damianwajser.validator.constraint.number;

import com.github.damianwajser.validator.annotation.number.Digits;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Optional;

public class DigitsConstraint extends AbstractConstraint implements ConstraintValidator<Digits, Object> {

	private int maxIntegerLength;
	private int maxFractionLength;
	private int multipleOf;

	@Override
	public void initialize(Digits field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
		this.maxIntegerLength = field.integer();
		this.maxFractionLength = field.fraction();
		this.multipleOf = field.multipleOf();
		this.validateParameters();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return getBigDecimal(field).map(d->this.errorInPrecision(d) || this.hasErrorInReminder(d)).orElse(true);
	}

	private boolean hasErrorInReminder(BigDecimal bigDecimal) {
		boolean hasError = this.multipleOf == 0 ? false : !bigDecimal.remainder(new BigDecimal(this.multipleOf)).equals(BigDecimal.ZERO);
		return hasError;
	}

	private boolean errorInPrecision(BigDecimal bigDecimal) {
		int integerPartLength = bigDecimal.precision() - bigDecimal.scale();
		int fractionPartLength = bigDecimal.scale() < 0 ? 0 : bigDecimal.scale();
		boolean hasError =  this.maxIntegerLength < integerPartLength || this.maxFractionLength < fractionPartLength;
		return hasError;
	}

	private Optional<BigDecimal> getBigDecimal(Object field) {
		BigDecimal bigNum = null;
		if (field != null && field.getClass().isAssignableFrom(BigDecimal.class)) {
			bigNum = (BigDecimal) field;
		} else if (field != null) {
			bigNum = new BigDecimal(field.toString());
		}
		return Optional.ofNullable(bigNum);
	}

	private void validateParameters() {
		if (this.maxIntegerLength < 0) {
			throw new IllegalArgumentException("The length of the integer part cannot be negative.");
		} else if (this.maxFractionLength < 0) {
			throw new IllegalArgumentException("The length of the fraction part cannot be negative.");
		}
	}
}
