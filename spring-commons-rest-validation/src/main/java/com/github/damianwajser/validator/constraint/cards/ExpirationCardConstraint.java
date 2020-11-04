package com.github.damianwajser.validator.constraint.cards;

import com.github.damianwajser.validator.annotation.cards.CardExpiration;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.interfaces.CardExpirable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;

public class ExpirationCardConstraint extends AbstractConstraint implements ConstraintValidator<CardExpiration, Object> {

	CardExpiration.YearFormat yearFormat;

	@Override
	public void initialize(CardExpiration field) {
		super.excludes = field.excludes();
		this.yearFormat = field.yearFormat();
	}

	@Override
	protected boolean hasError(Object field, ConstraintValidatorContext cxt) {
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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.getPattern()+"-dd")
					.withResolverStyle(ResolverStyle.STRICT);

			TemporalAccessor temporalAccessor = formatter.parse(dateStr + "-01");

			LocalDate expiryDate = LocalDate.of(YearMonth.from(temporalAccessor).getYear(),
					MonthDay.from(temporalAccessor).getMonthValue(),
					MonthDay.from(temporalAccessor).getDayOfMonth());

			return LocalDate.now().isBefore(expiryDate);
		} catch (Exception e) {
			return false;
		}
	}

	private String getPattern() {
		String format;
		switch (this.yearFormat) {
			case TWO_DIGITS:
				format = "M-uu";
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
