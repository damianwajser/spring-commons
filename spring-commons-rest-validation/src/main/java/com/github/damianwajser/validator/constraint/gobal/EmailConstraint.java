package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.Email;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailConstraint extends AbstractConstraint implements ConstraintValidator<Email, Object> {

	private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

	@Override
	public void initialize(Email field) {
		super.excludes = field.excludes();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return !(field != null && pattern.matcher(field.toString()).matches());
	}

}
