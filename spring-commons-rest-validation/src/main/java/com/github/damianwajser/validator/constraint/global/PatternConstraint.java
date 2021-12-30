package com.github.damianwajser.validator.constraint.global;

import com.github.damianwajser.validator.annotation.global.Pattern;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 */
public class PatternConstraint extends AbstractConstraint implements ConstraintValidator<Pattern, Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatternConstraint.class);

	private String regex;

	@Override
	public void initialize(Pattern field) {
		this.initialize(field.excludes(), field.onlyIn(), field.regexp(), field.isNulleable());
	}

	public PatternConstraint initialize(HttpMethod[] excludes, HttpMethod[] onlyIn, String regex, boolean isNulleable) {
		super.initialize(excludes, onlyIn, isNulleable);
		this.regex = regex;
		return this;
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		LOGGER.debug("check: {}, pattern: {}", field, regex);
		boolean hasError = true;
		if (field != null && String.class.isAssignableFrom(field.getClass())) {
			hasError = !((String) field).matches(this.regex);
		}
		return hasError;
	}

}
