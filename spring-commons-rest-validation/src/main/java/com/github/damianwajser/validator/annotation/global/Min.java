package com.github.damianwajser.validator.annotation.global;

import com.github.damianwajser.validator.constraint.gobal.MinConstraint;
import org.springframework.http.HttpMethod;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {MinConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Min {

	HttpMethod[] excludes() default {};

	String message() default "{javax.validation.constraints.min.message}";

	Class<?>[] groups() default {};

	long min();

	Class<? extends Payload>[] payload() default {};

	String businessCode();

}
