package com.github.damianwajser.validator.constraint.global;

import com.github.damianwajser.validator.annotation.global.AssertTrue;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AssertTrueConstraint extends AbstractConstraint implements ConstraintValidator<AssertTrue, Object> {

	@Override
	public void initialize(AssertTrue field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean result = true;
		if (field != null && field.getClass().isAssignableFrom(Boolean.class)) {
			result = !(Boolean) field;
		}
		return result;
	}

}
