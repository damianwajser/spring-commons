package com.github.damianwajser.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import com.github.damianwajser.validator.annotation.NotEmpty;

public class NotEmptyConstraint extends AbstractConstraint implements ConstraintValidator<NotEmpty, String> {

	private HttpMethod[] excludes;

	@Override
	public void initialize(NotEmpty field) {
		this.excludes = field.excludes();
	}

	@Override
	public boolean isValid(String field, ConstraintValidatorContext cxt) {
		if (methodExclude(this.excludes)) {
			return !StringUtils.isEmpty(field);
		}
		return true;
	}

}
