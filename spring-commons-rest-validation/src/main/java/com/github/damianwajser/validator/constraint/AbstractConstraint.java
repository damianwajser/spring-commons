package com.github.damianwajser.validator.constraint;

import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractConstraint {

	protected HttpMethod[] excludes;
	protected HttpMethod[] onlyIn;
	protected boolean isNulleable;

	protected void initialize(HttpMethod[] excludes, HttpMethod[] onlyIn, boolean isNulleable) {
		this.excludes = excludes;
		this.onlyIn = onlyIn;
		this.isNulleable = isNulleable;
	}

	protected abstract boolean hasError(Object field, ConstraintValidatorContext cxt);

	protected Optional<HttpServletRequest> getCurrentHttpRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
				.filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
				.map(ServletRequestAttributes.class::cast)
				.map(ServletRequestAttributes::getRequest);
	}

	protected Optional<HttpMethod> getCurrentHttpMethod() {
		return this.getCurrentHttpRequest().map(r -> HttpMethod.resolve(r.getMethod()));
	}

	/**
	 * @return true if has exlude request method, false in Other case
	 */
	protected boolean currentHttpMethodIsSkippeable() {
		Optional<HttpMethod> method = this.getCurrentHttpMethod();
		List<HttpMethod> methodsList = Arrays.asList(this.onlyIn);
		// if method is blank not exclude validation for request: return false
		return method.isPresent() && (methodsList.isEmpty() ? isExcluded(method.get()) : !methodsList.contains(method.get()));
	}

	private boolean isExcluded(HttpMethod current) {
		return Arrays.asList(this.excludes).contains(current);
	}

	public boolean isValid(Object field, ConstraintValidatorContext cxt) {
		boolean isValid = true;
		if (!currentHttpMethodIsSkippeable()) {
			if (field != null) {
				isValid = !this.hasError(field, cxt);
			} else if (!this.isNulleable) {
				isValid = false;
			}
		}
		return isValid;
	}

}
