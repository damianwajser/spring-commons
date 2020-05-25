package com.github.damianwajser.validator.constraint;

import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractConstraint {

	protected HttpMethod[] excludes;

	public abstract boolean applyConnstraint(String field, ConstraintValidatorContext cxt);

	protected Optional<HttpServletRequest> getCurrentHttpRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).filter(
				requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
				.map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
				.map(ServletRequestAttributes::getRequest);
	}

	protected Optional<HttpMethod> getCurrentHttpMethod() {
		return this.getCurrentHttpRequest().map(r -> HttpMethod.resolve(r.getMethod()));
	}

	protected boolean methodExclude(HttpMethod[] excludes) {
		Optional<HttpMethod> method = this.getCurrentHttpMethod();
		return method.isPresent() ? !Arrays.asList(excludes).contains(method) : false;
	}

	public boolean isValid(String field, ConstraintValidatorContext cxt) {
		if (methodExclude(this.excludes)) {
			return !StringUtils.isEmpty(field);
		}
		return true;
	}

}
