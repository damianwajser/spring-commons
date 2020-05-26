package com.github.damianwajser.validator.annotation;

import com.github.damianwajser.validator.constraint.NotEmptyConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { NotEmptyConstraint.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface NotEmpty {

	HttpMethod[] excludes() default {};

	String message() default "{javax.validation.constraints.NotEmpty.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String businessCode() default "400";

	/**
	 * Defines several {@code @NotEmpty} constraints on the same element.
	 *
	 * @see NotEmpty
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		NotEmpty[] value();
	}

}
