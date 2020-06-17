package com.github.damianwajser.validator.annotation.strings;

import com.github.damianwajser.validator.constraint.strings.PasswordConstraint;
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
@Constraint(validatedBy = {PasswordConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Password.List.class)
public @interface Password {

	HttpMethod[] excludes() default {};

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String businessCode();

	boolean isNulleable() default false;

	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		Password[] value();
	}
}
