package com.github.damianwajser.model.cards;

import com.github.damianwajser.validator.annotation.cards.CardExpiration;
import com.github.damianwajser.validator.interfaces.CardExpirable;

@CardExpiration(message = "as", businessCode = "c-400")
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

	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}

	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@Override
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	@Override
	public int getExpirationYear() {
		return this.expirationYear;
	}
}
