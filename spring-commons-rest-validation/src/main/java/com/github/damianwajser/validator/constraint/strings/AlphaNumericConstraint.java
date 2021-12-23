package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.AlphaNumeric;
import com.github.damianwajser.validator.constraint.strings.abstracts.AbstractWordConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;

public class AlphaNumericConstraint extends AbstractWordConstraint implements ConstraintValidator<AlphaNumeric, Object> {

	@Override
	public void initialize(AlphaNumeric field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
		super.max = field.max();
		super.min = field.min();
		super.allowSpaces = field.allowSpaces();
	}

	protected boolean isAlpha(Object field, boolean allowSpaces) {
		return allowSpaces ? !StringUtils.isAlphanumericSpace((String) field) : !StringUtils.isAlphanumeric((String) field);
	}
}
