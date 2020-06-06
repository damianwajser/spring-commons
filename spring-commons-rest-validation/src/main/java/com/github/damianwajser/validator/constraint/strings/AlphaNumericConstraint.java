package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.AlphaNumeric;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.gobal.SizeConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphaNumericConstraint extends AbstractConstraint implements ConstraintValidator<AlphaNumeric, Object> {
	int max;
	int min;
	boolean allowSpaces;

	@Override
	public void initialize(AlphaNumeric field) {
		super.excludes = field.excludes();
		this.max = field.max();
		this.min = field.min();
		this.allowSpaces = field.allowSpaces();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null && String.class.isAssignableFrom(field.getClass())) {
			hasError = new SizeConstraint().initialize(excludes, max, min).hasError(field, cxt);
			if (!hasError) {
				hasError = allowSpaces ? !StringUtils.isAlphanumericSpace((String) field) : !StringUtils.isAlphanumeric((String) field);
			}
		}
		return hasError;
	}

}
