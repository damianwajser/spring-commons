package com.github.damianwajser.validator.constraint;

import com.github.damianwajser.validator.annotation.NotEmpty;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyConstraint extends AbstractConstraint implements ConstraintValidator<NotEmpty, String> {

	@Override
	public void initialize(NotEmpty field) {
		super.excludes = field.excludes();
	}

	@Override
	public boolean applyConnstraint(String field, ConstraintValidatorContext cxt) {
		return !StringUtils.isEmpty(field);
	}

}
