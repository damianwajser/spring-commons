package com.github.damianwajser.exceptions.handlers;

import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = RestException.class)
	protected ResponseEntity<ErrorMessage> handleConflict(RestException ex, HttpServletRequest request) {
		return new ResponseEntity<>(ex.getErrorMessage(request), ex.getHttpCode());
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
		return new ResponseEntity<>(new ErrorMessage(getExceptionDetails(results), request),
				HttpStatus.BAD_REQUEST);
	}

	private List<ExceptionDetail> getExceptionDetails(BindingResult results) {
		return results.getFieldErrors().stream().map(FieldErrorMapper::convert).collect(Collectors.toList());
	}
}
