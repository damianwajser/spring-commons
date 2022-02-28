package com.github.damianwajser.validator.constraint.enums;

import com.github.damianwajser.validator.annotation.enums.MatchEnum;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchEnumConstraint extends AbstractConstraint implements ConstraintValidator<MatchEnum, Object> {

	private List<String> acceptedValues;

	@Override
	public void initialize(MatchEnum field) {
		this.initialize(field.excludes(), field.onlyIn(), field.isNulleable(), field.enumClass());
	}

	public MatchEnumConstraint initialize(HttpMethod[] excludes, HttpMethod[] onlyIn, boolean isNulleable, Class<? extends Enum<?>> enumClass) {
		super.initialize(excludes, onlyIn, isNulleable);
		this.acceptedValues = Stream.of(enumClass.getEnumConstants())
				.map(Enum::name)
				.collect(Collectors.toList());
		return this;
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		Stream<Object> stream = field.getClass().isArray() ? Arrays.stream((Object[]) field) : Stream.of(field);
		return !acceptedValues.containsAll(stream
				.map(Object::toString)
				.collect(Collectors.toList()));
	}
}
