package com.github.damianwajser.validator.constraint;

import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractConstraint {

	protected HttpMethod[] excludes;
	protected boolean isNulleable;

	public abstract boolean hasError(Object field, ConstraintValidatorContext cxt);

	protected boolean hasError(Object field) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return !validator.validate(field).isEmpty();
	}

	protected Optional<HttpServletRequest> getCurrentHttpRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).filter(
				requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
				.map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
				.map(ServletRequestAttributes::getRequest);
	}

	protected Optional<HttpMethod> getCurrentHttpMethod() {
		return this.getCurrentHttpRequest().map(r -> HttpMethod.resolve(r.getMethod()));
	}

	/**
	 * @return true if has exlude request method, false in Other case
	 */
	protected boolean isExcluded() {
		Optional<HttpMethod> method = this.getCurrentHttpMethod();
		// if method is blank not exclude validation for request: return false
		return method.isPresent() ? Arrays.asList(this.excludes).contains(method.get()) : Boolean.FALSE;
	}

	public boolean isValid(Object field, ConstraintValidatorContext cxt) {
		boolean isValid = true;
		if (!isExcluded()) {
			if (field != null) {
				isValid = !this.hasError(field, cxt);
			} else if (!this.isNulleable) {
				isValid = false;
			}
		}
		return isValid;
	}

}
