package com.github.damianwajser.validator.annotation.global;

import com.github.damianwajser.validator.constraint.global.MaxConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * @deprecated
 * This method is no longer acceptable to compute time between versions.
 * <p> Use {@link com.github.damianwajser.validator.annotation.number.Min} instead.
 */
@Deprecated
@Documented
@Constraint(validatedBy = {MaxConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Max.List.class)
public @interface Max {

	HttpMethod[] excludes() default {};

	String message() default "{javax.validation.constraints.max.message}";

	Class<?>[] groups() default {};

	long max();

	Class<? extends Payload>[] payload() default {};

	String businessCode();

	boolean isNulleable() default false;

	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		Max[] value();
	}
}
