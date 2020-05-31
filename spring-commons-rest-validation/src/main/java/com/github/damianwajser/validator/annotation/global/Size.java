package com.github.damianwajser.validator.annotation.global;

import com.github.damianwajser.validator.constraint.gobal.SizeConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {SizeConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Size {

	HttpMethod[] excludes() default {};

	String message() default "{javax.validation.constraints.Size.message}";

	Class<?>[] groups() default {};

	int max();

	int min() default 0;

	Class<? extends Payload>[] payload() default {};

	String businessCode();

}
