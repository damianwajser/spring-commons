package com.github.damianwajser.exceptions.handlers;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<ErrorMessage> handleDefault(Exception ex, HttpServletRequest request, Locale locale) {
		return new ResponseEntity<>(new ErrorMessage(Arrays.asList(new ExceptionDetail(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getMessage(), Optional.empty())), request), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = RestException.class)
	protected ResponseEntity<ErrorMessage> handleConflict(RestException ex, HttpServletRequest request, Locale locale) {
		return new ResponseEntity<>(new ErrorMessage(this.getExceptionDetails(ex, locale), request), ex.getHttpCode());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
		return validationBinnding(ex.getBindingResult(), request);
	}

	@ExceptionHandler(NestedRuntimeException.class)
	public ResponseEntity<ErrorMessage> handleNestedRuntimeException(NestedRuntimeException ex, HttpServletRequest request) {
		return new ResponseEntity<>(new ErrorMessage(getExceptionDetails(ex), request),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(ConstraintViolationException ex, HttpServletRequest request) {
		return validationBinnding(ex.getConstraintViolations(), request);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(BindException ex, HttpServletRequest request) {
		return validationBinnding(ex.getBindingResult(), request);
	}

	private ResponseEntity<ErrorMessage> validationBinnding(BindingResult results, HttpServletRequest request) {
		return new ResponseEntity<>(new ErrorMessage(getExceptionDetails(results), request),
				HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<ErrorMessage> validationBinnding(Set<ConstraintViolation<?>> results, HttpServletRequest request) {
		return new ResponseEntity<>(new ErrorMessage(getExceptionDetails(results), request),
				HttpStatus.BAD_REQUEST);
	}

	private List<ExceptionDetail> getExceptionDetails(Set<ConstraintViolation<?>> results) {
		return results.stream().map(ExceptionDetailMapper::convert).collect(Collectors.toList());
	}

	private List<ExceptionDetail> getExceptionDetails(NestedRuntimeException ex) {
		Throwable cause = ex.getMostSpecificCause();
		String message = Strings.EMPTY;
		try {
			message = FieldUtils.readField(cause, "detailMessage", true).toString();
		} catch (Exception e) {
			message = cause.getLocalizedMessage();
		}
		return Arrays.asList(new ExceptionDetail("400", message, Optional.empty()));
	}

	private List<ExceptionDetail> getExceptionDetails(BindingResult results) {
		return results.getFieldErrors().stream().map(ExceptionDetailMapper::convert).collect(Collectors.toList());
	}

	private List<ExceptionDetail> getExceptionDetails(RestException ex, Locale locale) {
		return ex.getDetails().stream().map(d -> ExceptionDetailMapper.internacionalizate(d, messageSource, locale)).collect(Collectors.toList());
	}

}
