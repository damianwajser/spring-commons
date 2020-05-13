package com.github.damianwajser.exceptions.handlers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.handlers.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = RestException.class)
	protected ResponseEntity<ErrorMessage> handleConflict(RestException ex, HttpServletRequest request) {
		return new ResponseEntity<ErrorMessage>(new ErrorMessage(ex.getDetails(), getPath(request)),
				getHttpCode(ex.getClass()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		return validationBinnding(ex.getBindingResult(), request);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(BindException ex, HttpServletRequest request) {
		return validationBinnding(ex.getBindingResult(), request);
	}

	private ResponseEntity<ErrorMessage> validationBinnding(BindingResult results, HttpServletRequest request) {
		return new ResponseEntity<ErrorMessage>(new ErrorMessage(getExceptionDetails(results), getPath(request)),
				HttpStatus.BAD_REQUEST);
	}

	private List<ExceptionDetail> getExceptionDetails(BindingResult results) {

		return results.getFieldErrors().stream().map(error -> {
			Map<String, Object> attributes = error.unwrap(javax.validation.ConstraintViolation.class)
					.getConstraintDescriptor().getAttributes();
			String code = HttpStatus.BAD_GATEWAY.toString();
			if (attributes != null) {
				code = attributes.getOrDefault("businessCode", "400").toString();
			}
			ExceptionDetail detail = new ExceptionDetail(code, error.getDefaultMessage(),
					Optional.of(error.getField()));
			detail.setMetaData("rejectedValue", error.getRejectedValue());
			detail.setMetaData("field", error.getField());
			detail.setMetaData("reason", error.getCode());
			return detail;
		}).collect(Collectors.toList());
	}

	private String getPath(HttpServletRequest request) {
		return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	}

	private HttpStatus getHttpCode(Class<?> ex) {
		ResponseStatus rs = ex.getAnnotation(ResponseStatus.class);
		if (rs != null) {
			return rs.code();
		} else {
			return getHttpCode(ex.getSuperclass());
		}
	}
}