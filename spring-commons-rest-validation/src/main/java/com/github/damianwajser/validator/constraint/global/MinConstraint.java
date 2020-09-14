package com.github.damianwajser.validator.constraint.global;

import com.github.damianwajser.validator.annotation.global.Min;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Deprecated
/**
 *
 */
public class MinConstraint extends AbstractConstraint implements ConstraintValidator<Min, Object> {

	long min;

	@Override
	public void initialize(Min field) {
		super.excludes = field.excludes();
		super.isNulleable = field.isNulleable();
		this.min = field.min();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null && Number.class.isAssignableFrom(field.getClass())) {
			long fieldMin = ((Number) field).longValue();
			hasError = fieldMin <= min;
		}
		return hasError;
	}

}
