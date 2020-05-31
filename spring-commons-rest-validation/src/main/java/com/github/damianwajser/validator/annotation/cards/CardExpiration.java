package com.github.damianwajser.validator.annotation.cards;

import com.github.damianwajser.validator.constraint.cards.ExpirationCardConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ExpirationCardConstraint.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface CardExpiration {

	HttpMethod[] excludes() default {};

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String businessCode();

	YearFormat yearFormat() default YearFormat.TWO_DIGITS;

	public enum YearFormat {
		TWO_DIGITS, FOUR_DIGIT;
	}
}
