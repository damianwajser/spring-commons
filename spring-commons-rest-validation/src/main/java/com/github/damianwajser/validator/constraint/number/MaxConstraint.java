package com.github.damianwajser.validator.constraint.number;

import com.github.damianwajser.validator.annotation.number.Max;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 */
public class MaxConstraint extends AbstractConstraint implements ConstraintValidator<Max, Object> {

	long max;

	@Override
	public void initialize(Max field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
		this.max = field.value();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null && Number.class.isAssignableFrom(field.getClass())) {
			long fieldMax = ((Number) field).longValue();
			hasError = fieldMax > max;
		}
		return hasError;
	}

}
