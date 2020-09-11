package com.github.damianwajser.validator.constraint.global;

import com.github.damianwajser.validator.annotation.global.Max;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 */
public class MaxConstraint extends AbstractConstraint implements ConstraintValidator<Max, Object> {

	long max;

	@Override
	public void initialize(Max field) {
		super.excludes = field.excludes();
		super.isNulleable = field.isNulleable();
		long defaultValue = (Long)AnnotationUtils.getDefaultValue(field);
		this.max = field.max();
		if (max == defaultValue) {
			max = field.value();
		}
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null && Number.class.isAssignableFrom(field.getClass())) {
			long fieldMax = ((Number) field).longValue();
			hasError = fieldMax > max;
		}
		return hasError;
	}

}
