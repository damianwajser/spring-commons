package com.github.damianwajser.model.cards.tokens;

import com.github.damianwajser.validator.annotation.cards.CardToken;

public class CardDataFastTokenObject {

	@CardToken(provider = CardToken.Tokenizer.DATA_FAST, message = "some message", businessCode = "c-400")
	private String value;

	public CardDataFastTokenObject() {
	}

	public CardDataFastTokenObject(String value) {
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
