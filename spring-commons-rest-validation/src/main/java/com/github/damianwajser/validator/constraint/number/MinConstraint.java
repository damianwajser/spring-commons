package com.github.damianwajser.validator.constraint.number;

import com.github.damianwajser.validator.annotation.number.Min;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 */
public class MinConstraint extends AbstractConstraint implements ConstraintValidator<Min, Object> {

	long min;

	@Override
	public void initialize(Min field) {
		super.excludes = field.excludes();
		super.isNulleable = field.isNulleable();
		this.min = field.value();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null && Number.class.isAssignableFrom(field.getClass())) {
			long fieldMin = ((Number) field).longValue();
			hasError = fieldMin < min;
		}
		return hasError;
	}

}
