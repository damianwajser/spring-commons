package com.github.damianwajser.validator.constraint.strings.abstracts;

import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.global.SizeConstraint;

import javax.validation.ConstraintValidatorContext;

public abstract class AbstractWordConstraint extends AbstractConstraint {

	protected int max;
	protected int min;
	protected boolean allowSpaces;


	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (String.class.isAssignableFrom(field.getClass())) {
			hasError = !new SizeConstraint().initialize(this.excludes, this.onlyIn, this.max, this.min, this.isNulleable).isValid(field, cxt);
			if (!hasError) {
				hasError = isAlpha(field, this.allowSpaces);
			}
		}
		return hasError;
	}

	protected abstract boolean isAlpha(Object field, boolean allowSpaces);

}
