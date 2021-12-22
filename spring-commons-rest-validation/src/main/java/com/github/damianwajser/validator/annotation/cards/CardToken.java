package com.github.damianwajser.validator.annotation.cards;

import com.github.damianwajser.validator.constraint.cards.CardTokenConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CardTokenConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(CardToken.List.class)
public @interface CardToken {

	HttpMethod[] excludes() default {};

	HttpMethod[] onlyIn() default {};

	String message();

	Class<?>[] groups() default {};

	Tokenizer provider();

	Class<? extends Payload>[] payload() default {};

	String businessCode();

	boolean isNulleable() default false;

	enum Tokenizer {
		TOKEN_EX;
	}

	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	@Retention(RUNTIME)
	@Documented
	@interface List {

		CardToken[] value();
	}
}
