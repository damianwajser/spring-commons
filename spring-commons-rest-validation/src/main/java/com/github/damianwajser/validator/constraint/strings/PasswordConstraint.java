package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.Password;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.global.PatternConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraint extends AbstractConstraint implements ConstraintValidator<Password, Object> {

	private static final String PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

	@Override
	public void initialize(Password field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return !new PatternConstraint().initialize(this.excludes, this.onlyIn, PATTERN, super.isNulleable).isValid(field, cxt);
	}

}
