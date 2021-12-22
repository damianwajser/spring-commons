package com.github.damianwajser.model.cards;

import com.github.damianwajser.validator.annotation.cards.CardExpiration;
import com.github.damianwajser.validator.interfaces.CardExpirable;

@CardExpiration(message = "some message", businessCode = "c-400")
public class ExpirationObject implements CardExpirable {

	private int expirationMonth;
	private int expirationYear;

	public ExpirationObject() {
	}

	public ExpirationObject(int expirationMonth, int expirationYear) {
		super();
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
	}

	@Override
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@Override
	public int getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}
}
