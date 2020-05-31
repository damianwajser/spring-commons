package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.AssertTrue;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AssertTrueConstraint extends AbstractConstraint implements ConstraintValidator<AssertTrue, Object> {

	@Override
	public void initialize(AssertTrue field) {
		super.excludes = field.excludes();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean result = true;
		if (field != null && field.getClass().isAssignableFrom(Boolean.class)) {
			result = !(Boolean) field;
		}
		return result;
	}

}
