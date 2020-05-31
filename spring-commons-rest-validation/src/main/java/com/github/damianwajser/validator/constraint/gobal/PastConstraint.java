package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.Past;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PastConstraint extends AbstractConstraint implements ConstraintValidator<Past, Object> {

	@Override
	public void initialize(Past field) {
		super.excludes = field.excludes();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean result = true;
		if (field != null) {
			if (LocalDate.class.isAssignableFrom(field.getClass())) {
				result = LocalDate.now().isBefore((LocalDate) field);
			} else if (LocalDateTime.class.isAssignableFrom(field.getClass())) {
				result = LocalDateTime.now().isBefore((LocalDateTime) field);
			} else if (LocalDateTime.class.isAssignableFrom(field.getClass())) {
				result = LocalDateTime.now().isBefore((LocalDateTime) field);
			} else if (Date.class.isAssignableFrom(field.getClass())) {
				LocalDateTime.now().isBefore(LocalDateTime.ofInstant(((Date) field).toInstant(), ZoneId.systemDefault()));
			}
		}
		return result;
	}
}
