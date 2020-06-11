package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.Password;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraint extends AbstractConstraint implements ConstraintValidator<Password, Object> {

	private static final String PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

	@Override
	public void initialize(Password field) {
		super.excludes = field.excludes();
		super.isNulleable = field.isNulleable();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return new PatternConstraint().initialize(this.excludes, PATTERN, super.isNulleable).hasError(field, cxt);
	}

}
