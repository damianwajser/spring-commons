package com.github.damianwajser.validator.constraint.cards.token.validators;

import java.util.regex.Pattern;

public class TokenExValidator implements TokenValidator {

	private static final Pattern pattern = Pattern.compile("[0-9A-z]{13,19}");

	@Override
	public boolean isValid(String value) {
		return pattern.matcher(value).matches();
	}
}
