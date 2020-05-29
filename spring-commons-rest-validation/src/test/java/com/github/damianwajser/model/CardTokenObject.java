package com.github.damianwajser.model;

import com.github.damianwajser.validator.annotation.cards.CardToken;
import com.github.damianwajser.validator.annotation.global.NotEmpty;
import org.springframework.http.HttpMethod;

public class CardTokenObject {

	@CardToken(provider = CardToken.Tokenizer.TOKEN_EX, message = "ago", excludes = HttpMethod.PUT, businessCode = "c-400")
	private String value;

	public CardTokenObject() {
	}

	public CardTokenObject(String value) {
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
