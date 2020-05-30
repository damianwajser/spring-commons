package com.github.damianwajser.validator.constraint.cards.token.validators;

import com.github.damianwajser.validator.annotation.cards.CardToken;

public final class TokenValidatorsFactory {

	private TokenValidatorsFactory() {
		//make factory static
	}

	public static TokenValidator getTokenValidator(CardToken.Tokenizer tokenizer) {
		return new TokenExValidator();
	}
}
