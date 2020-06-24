package com.github.damianwajser.validator.constraint.enums;

import com.github.damianwajser.validator.annotation.enums.Country_ISO3166;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.enums.values.Countries;
import org.springframework.http.HttpMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountryIso3166Constraint extends AbstractConstraint implements ConstraintValidator<Country_ISO3166, Object> {

	@Override
	public void initialize(Country_ISO3166 field) {
		this.initialize(field.excludes(), field.isNulleable(), Countries.class);
	}

	public CountryIso3166Constraint initialize(HttpMethod[] excludes, boolean isNulleable, Class<? extends Enum<?>> enumClass) {
		super.excludes = excludes;
		super.isNulleable = isNulleable;
		return this;
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return !new MatchEnumConstraint().initialize(this.excludes, this.isNulleable, Countries.class).isValid(field, cxt);
	}
}
