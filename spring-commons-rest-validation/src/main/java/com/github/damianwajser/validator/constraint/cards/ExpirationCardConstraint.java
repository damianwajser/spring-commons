package com.github.damianwajser.validator.constraint.cards;

import com.github.damianwajser.validator.annotation.cards.CardExpiration;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.interfaces.CardExpirable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpirationCardConstraint extends AbstractConstraint implements ConstraintValidator<CardExpiration, Object> {

	CardExpiration.YearFormat yearFormat;

	@Override
	public void initialize(CardExpiration field) {
		super.excludes = field.excludes();
		this.yearFormat = field.yearFormat();
	}

	@Override
	public boolean hasError(Object field, ConstraintValidatorContext cxt) {
		boolean result = true;
		if (field != null) {
			Class<?> clazz = field.getClass();
			if (CardExpirable.class.isAssignableFrom(clazz)) {
				CardExpirable expirable = (CardExpirable) field;
				result = !isValidExpirable(expirable);
			}
		}
		return result;
	}

	private boolean isValidExpirable(CardExpirable expirable) {
		String dateStr = expirable.getExpirationMonth() + "-" + expirable.getExpirationYear();
		try {
			DateFormat sdf = new SimpleDateFormat(this.getPattern());
			sdf.setLenient(false);
			Date date = sdf.parse(dateStr);
			return date.after(new Date());
		} catch (ParseException e) {
			return false;
		}
	}

	private String getPattern() {
		String format;
		switch (this.yearFormat) {
			case TWO_DIGITS:
				format = "MM-YY";
				break;
			case FOUR_DIGIT:
				format = "MM-YYYY";
				break;
			default:
				format = null;
		}
		return format;
	}
}
