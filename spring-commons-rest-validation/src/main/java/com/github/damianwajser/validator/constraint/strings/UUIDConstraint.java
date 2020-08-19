package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.UUID;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.gobal.PatternConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UUIDConstraint extends AbstractConstraint implements ConstraintValidator<UUID, Object> {

	private static final String PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

	@Override
	public void initialize(UUID field) {
		super.excludes = field.excludes();
		super.isNulleable = field.isNulleable();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return !new PatternConstraint().initialize(this.excludes, PATTERN, this.isNulleable).isValid(field, cxt);
	}
}
