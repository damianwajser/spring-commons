package com.github.damianwajser.validator.constraint.strings;

import com.github.damianwajser.validator.annotation.strings.Word;
import com.github.damianwajser.validator.constraint.strings.abstracts.AbstractWordConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;

public class WordConstraint extends AbstractWordConstraint implements ConstraintValidator<Word, Object> {

	@Override
	public void initialize(Word field) {
		super.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
		super.max = field.max();
		super.min = field.min();
		super.allowSpaces = field.allowSpaces();
	}

	protected boolean isAlpha(Object field, boolean allowSpaces) {
		return allowSpaces ? !StringUtils.isAlphaSpace((String) field) : !StringUtils.isAlpha((String) field);
	}
}
