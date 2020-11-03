package com.github.damianwajser.validator.constraint.cards;

import com.github.damianwajser.validator.annotation.cards.CardExpiration;
import com.github.damianwajser.validator.constraint.AbstractConstraint;
import com.github.damianwajser.validator.interfaces.CardExpirable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Comparator;
import java.util.Date;

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
			TemporalAccessor ta = DateTimeFormatter.ofPattern(this.getPattern()).parse(dateStr);
			Comparator<TemporalAccessor> monthCompare = (a, b) -> a.get(ChronoField.MONTH_OF_YEAR) - b.get(ChronoField.MONTH_OF_YEAR);
			Comparator<TemporalAccessor> yearCompare = (a, b) -> a.get(ChronoField.YEAR) - b.get(ChronoField.YEAR);
			Comparator<TemporalAccessor> dateCompare = monthCompare.thenComparing(yearCompare);

			return dateCompare.compare(ta, LocalDateTime.now()) < 0;
		} catch (Exception e) {
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
