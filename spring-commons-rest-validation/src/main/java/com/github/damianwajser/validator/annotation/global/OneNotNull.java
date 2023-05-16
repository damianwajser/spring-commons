package com.github.damianwajser.validator.annotation.global;

import com.github.damianwajser.validator.constraint.global.OneNotNullValidator;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {OneNotNullValidator.class})
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RUNTIME)
@Repeatable(OneNotNull.List.class)
public @interface OneNotNull {

	HttpMethod[] excludes() default {};

	HttpMethod[] onlyIn() default {};

	String message() default "{javax.validation.constraints.AssertTrue.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String businessCode();

	boolean isNulleable() default false;

	/**
	 * Fields to validate against null.
	 */
	String[] fields() default {};

	@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		OneNotNull[] value();
	}
}
