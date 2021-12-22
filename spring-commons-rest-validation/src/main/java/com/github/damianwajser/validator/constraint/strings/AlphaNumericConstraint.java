package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.AlphaNumeric;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.global.SizeConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphaNumericConstraint extends AbstractConstraint implements ConstraintValidator<AlphaNumeric, Object> {
	private int max;
	private int min;
	private boolean allowSpaces;

	@Override
	public void initialize(AlphaNumeric field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
		this.max = field.max();
		this.min = field.min();
		this.allowSpaces = field.allowSpaces();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (String.class.isAssignableFrom(field.getClass())) {
			hasError = !new SizeConstraint().initialize(excludes, this.onlyIn, max, min, this.isNulleable).isValid(field, cxt);
			if (!hasError) {
				hasError = allowSpaces ? !StringUtils.isAlphanumericSpace((String) field) : !StringUtils.isAlphanumeric((String) field);
			}
		}
		return hasError;
	}

}
