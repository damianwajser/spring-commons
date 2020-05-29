package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.NotEmpty;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class NotEmptyConstraint extends AbstractConstraint implements ConstraintValidator<NotEmpty, Object> {

	@Override
	public void initialize(NotEmpty field) {
		super.excludes = field.excludes();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return ObjectUtils.isEmpty(field);
	}

}
