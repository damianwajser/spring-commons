package com.github.damianwajser.validator.annotation.strings;

import com.github.damianwajser.validator.constraint.strings.AlphaNumericConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {AlphaNumericConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface AlphaNumeric {

	HttpMethod[] excludes() default {};

	String message();

	boolean allowSpaces() default true;

	Class<?>[] groups() default {};

	int max() default 254;

	int min() default 0;

	Class<? extends Payload>[] payload() default {};

	String businessCode();

	boolean isNulleable() default false;
}
