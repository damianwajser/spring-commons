package com.github.damianwajser.validator.constraint.global;

import com.github.damianwajser.validator.annotation.global.NotEmpty;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyConstraint extends AbstractConstraint implements ConstraintValidator<NotEmpty, Object> {

	@Override
	public void initialize(NotEmpty field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return ObjectUtils.isEmpty(field);
	}

}
