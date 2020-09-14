package com.github.damianwajser.validator.annotation.number;

import com.github.damianwajser.validator.constraint.number.MinConstraint;
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
@Constraint(validatedBy = {MinConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Min.List.class)
public @interface Min {

	HttpMethod[] excludes() default {};

	String message() default "{javax.validation.constraints.Min.message}";

	Class<?>[] groups() default {};

	long value();

	Class<? extends Payload>[] payload() default {};

	String businessCode();

	boolean isNulleable() default false;

	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		Min[] value();
	}
}
