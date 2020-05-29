package com.github.damianwajser.validator.constraint.gobal;

import com.github.damianwajser.validator.annotation.global.NotEmpty;
import com.github.damianwajser.validator.annotation.global.Size;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.*;
import javax.validation.constraints.Max;
import java.util.Collection;
/**
 *
 */
public class SizeConstraint extends AbstractConstraint implements ConstraintValidator<Size, Object> {

	int max;

	@Override
	public void initialize(Size field) {
		super.excludes = field.excludes();
		this.max = max;
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean result = true;
		if (field != null) {
			Class<?> clazz = field.getClass();
			if (String.class.isAssignableFrom(clazz)) {
				result = ((String) field).length() > max;
			} else if (Collection.class.isAssignableFrom(clazz)) {
				result = ((Collection) field).size() > max;
			}
		}
		return result;
	}

}
