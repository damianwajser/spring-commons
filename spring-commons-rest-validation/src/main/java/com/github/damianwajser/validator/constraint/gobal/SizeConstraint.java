package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.Size;
import com.github.damianwajser.validator.constraint.AbstractConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 *
 */
public class SizeConstraint extends AbstractConstraint implements ConstraintValidator<Size, Object> {

	int max;
	int min;

	@Override
	public void initialize(Size field) {
		super.excludes = field.excludes();
		this.max = field.max();
		this.min = field.min();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null) {
			Class<?> clazz = field.getClass();
			if (String.class.isAssignableFrom(clazz)) {
				hasError = ((String) field).length() > max;
			} else if (Collection.class.isAssignableFrom(clazz)) {
				hasError = ((Collection) field).size() > max;
			}
		} else {
			hasError = min > 0;
		}
		return hasError;
	}

}
