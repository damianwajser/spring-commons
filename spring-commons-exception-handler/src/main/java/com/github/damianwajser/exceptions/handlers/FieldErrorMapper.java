package com.github.damianwajser.exceptions.handlers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.github.damianwajser.exceptions.model.ExceptionDetail;

public class FieldErrorMapper {

	public static ExceptionDetail convert(FieldError error) {

		Map<String, Object> attributes = error.unwrap(javax.validation.ConstraintViolation.class)
				.getConstraintDescriptor().getAttributes();
		String code = HttpStatus.BAD_GATEWAY.toString();
		if (attributes != null) {
			code = attributes.getOrDefault("businessCode", "400").toString();
		}
		ExceptionDetail detail = new ExceptionDetail(code, error.getDefaultMessage(), Optional.of(error.getField()));
		detail.setMetaData("rejectedValue", error.getRejectedValue());
		detail.setMetaData("field", error.getField());
		detail.setMetaData("reason", error.getCode());
		return detail;

	}
}
