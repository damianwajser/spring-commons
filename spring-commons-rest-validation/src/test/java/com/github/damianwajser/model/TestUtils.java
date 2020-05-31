package com.github.damianwajser.model;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNot;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class TestUtils {
	private static Validator VALIDATOR;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		VALIDATOR = factory.getValidator();
	}

	public static Set<ConstraintViolation<Object>> validationFor(Object object, String fieldname) {
		return VALIDATOR.validateProperty(object, fieldname);
	}

	public static Set<ConstraintViolation<Object>> validationFor(Object object) {
		return VALIDATOR.validate(object);
	}

	public static String onField(String fieldname) {
		return fieldname;
	}

	public static Matcher<Set<ConstraintViolation<Object>>> succedes() {
		return new PassesValidation();
	}

	public static Matcher<Set<ConstraintViolation<Object>>> fails() {
		return new IsNot(new PassesValidation());
	}

	static class PassesValidation extends BaseMatcher<Set<ConstraintViolation<Object>>> {
		@Override
		public boolean matches(Object o) {
			boolean result = false;
			if (o instanceof Set) {
				result = ((Set) o).isEmpty();
			}
			return result;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("valid");
		}
	}
}
