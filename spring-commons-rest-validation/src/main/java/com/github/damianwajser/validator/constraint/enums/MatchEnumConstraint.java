package com.github.damianwajser.validator.constraint.enums;

import com.github.damianwajser.validator.annotation.enums.MatchEnum;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchEnumConstraint extends AbstractConstraint implements ConstraintValidator<MatchEnum, Object> {

	private List<String> acceptedValues;

	@Override
	public void initialize(MatchEnum field) {
		this.initialize(field.excludes(), field.isNulleable(), field.enumClass());
	}

	public MatchEnumConstraint initialize(HttpMethod[] excludes, boolean isNulleable, Class<? extends Enum<?>> enumClass) {
		super.excludes = excludes;
		super.isNulleable = isNulleable;
		this.acceptedValues = Stream.of(enumClass.getEnumConstants())
				.map(Enum::name)
				.collect(Collectors.toList());
		return this;
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return !acceptedValues.contains(field.toString());
	}
}
