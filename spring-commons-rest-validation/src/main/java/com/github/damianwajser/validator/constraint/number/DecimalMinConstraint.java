package com.github.damianwajser.validator.constraint.number;

import com.github.damianwajser.validator.annotation.number.DecimalMin;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class DecimalMinConstraint extends AbstractConstraint implements ConstraintValidator<DecimalMin, Object> {

	private BigDecimal minValue;
	private boolean inclusive;

	@Override
	public void initialize(DecimalMin field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
		try {
			this.minValue = new BigDecimal(field.value());
		} catch (NumberFormatException var3) {
			throw new IllegalArgumentException(String.format("%s does not represent a valid BigDecimal format.", field.value()), var3);
		}
		this.inclusive = field.inclusive();
	}


	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		int comparisonResult = this.compare((BigDecimal) field);
		return this.inclusive ? comparisonResult < 0 : comparisonResult <= 0;
	}

	protected int compare(BigDecimal number) {
		return number.compareTo(this.minValue);
	}
}
