package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.global.Pattern;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 */
public class PatternConstraint extends AbstractConstraint implements ConstraintValidator<Pattern, Object> {

	private String regex;

	@Override
	public void initialize(Pattern field) {
		this.initialize(field.excludes(), field.regexp());
	}

	public PatternConstraint initialize(HttpMethod[] excludes, String regex) {
		super.excludes = excludes;
		this.regex = regex;
		return this;
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null && String.class.isAssignableFrom(field.getClass())) {
			hasError = !((String) field).matches(this.regex);
		}
		return hasError;
	}

}
