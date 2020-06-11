package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.Size;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.springframework.http.HttpMethod;

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
		this.initialize(field.excludes(), field.max(), field.min(), field.isNulleable());
	}

	public SizeConstraint initialize(HttpMethod[] excludes, int max, int min, boolean isNulleable) {
		super.excludes = excludes;
		super.isNulleable = isNulleable;
		this.max = max;
		this.min = min;
		return this;
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean hasError = true;
		if (field != null) {
			Class<?> clazz = field.getClass();
			if (String.class.isAssignableFrom(clazz)) {
				hasError = hasErrorSize(((String) field).length());
			} else if (Collection.class.isAssignableFrom(clazz)) {
				hasError = hasErrorSize(((Collection) field).size());
			}
		} else {
			hasError = min > 0;
		}
		return hasError;
	}

	private boolean hasErrorSize(int size) {
		return size < min || size > max;
	}

}
