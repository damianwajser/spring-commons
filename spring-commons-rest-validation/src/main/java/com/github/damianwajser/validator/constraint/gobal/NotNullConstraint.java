package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.NotNull;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullConstraint extends AbstractConstraint implements ConstraintValidator<NotNull, Object> {

	@Override
	public void initialize(NotNull field) {
		super.excludes = field.excludes();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return !ObjectUtils.allNotNull(field);
	}

}
