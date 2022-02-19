package com.github.damianwajser.model.cards.tokens;

import com.github.damianwajser.validator.annotation.cards.CardToken;

public class CardTokenExObject {

	@CardToken(provider = CardToken.Tokenizer.TOKEN_EX, message = "some message", businessCode = "c-400")
	private String value;

	public CardTokenExObject() {
	}

	public CardTokenExObject(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
