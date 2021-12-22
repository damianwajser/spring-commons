package com.github.damianwajser.validator.constraint.enums;

import com.github.damianwajser.validator.annotation.enums.Country_ISO3166;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.enums.values.Countries;
import org.springframework.http.HttpMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryIso3166Constraint extends AbstractConstraint implements ConstraintValidator<Country_ISO3166, Object> {

	@Override
	public void initialize(Country_ISO3166 field) {
		this.initialize(field.excludes(), field.onlyIn(), field.isNulleable());
	}

	public CountryIso3166Constraint initialize(HttpMethod[] excludes, boolean isNulleable, HttpMethod[] onlyIn) {
		super.initialize(excludes, onlyIn, isNulleable);
		return this;
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
		return !new MatchEnumConstraint().initialize(this.excludes, this.onlyIn, this.isNulleable, Countries.class).isValid(field, cxt);
	}
}
