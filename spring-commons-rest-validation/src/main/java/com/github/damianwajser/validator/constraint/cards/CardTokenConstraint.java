package com.github.damianwajser.validator.constraint.cards;

import com.github.damianwajser.validator.annotation.cards.CardToken;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.constraint.cards.token.validators.TokenValidatorsFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardTokenConstraint extends AbstractConstraint implements ConstraintValidator<CardToken, Object> {

	CardToken.Tokenizer provider;

	@Override
	public void initialize(CardToken field) {
		super.excludes = field.excludes();
		this.provider = field.provider();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean result = true;
		if (field != null) {
			Class<?> clazz = field.getClass();
			if (String.class.isAssignableFrom(clazz)) {
				result = !TokenValidatorsFactory.getTokenValidator(provider).isValid((String)field);
			}
		}
		return result;
	}

}
