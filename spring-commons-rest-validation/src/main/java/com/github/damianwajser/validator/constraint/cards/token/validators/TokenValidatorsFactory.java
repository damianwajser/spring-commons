package com.github.damianwajser.validator.constraint.cards.token.validators;

import com.github.damianwajser.validator.annotation.cards.CardToken;

public final class TokenValidatorsFactory {

	private TokenValidatorsFactory() {
		//make factory static
	}

	public static TokenValidator getTokenValidator(CardToken.Tokenizer tokenizer) {
		TokenValidator tokenValidator = null;
		switch (tokenizer) {
			case TOKEN_EX:
				tokenValidator = new TokenExValidator();
				break;
			case DATA_FAST:
				tokenValidator = new DataFastValidator();
				break;
		}
		return tokenValidator;
	}
}
