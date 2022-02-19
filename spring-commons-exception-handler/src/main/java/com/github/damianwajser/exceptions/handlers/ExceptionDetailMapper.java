package com.github.damianwajser.exceptions.handlers;

import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class ExceptionDetailMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionDetailMapper.class);
	public static final String TEMPLATE_FORMAT_INCORRECT = "non compatible, the message not is a template";
	public static final String TEMPLATE_NOT_FOUND = "warning, template message no was changed";
	public static final String I18N_KEY = "i18n";

	private ExceptionDetailMapper() {
	}

	public static ExceptionDetail convert(ConstraintViolation error) {
		String code = error.getConstraintDescriptor().getAttributes().getOrDefault("businessCode", HttpStatus.BAD_REQUEST).toString();
		ExceptionDetail detail = new ExceptionDetail(code, error.getMessage(), Optional.of(error.getPropertyPath().toString()));
		detail.setMetaData("rejectedValue", error.getInvalidValue());
		detail.setMetaData("field", error.getPropertyPath().toString());
		detail.setMetaData("reason", error.getMessage());
		fillI18nWarnings(detail, error.getConstraintDescriptor().getMessageTemplate());
		return detail;
	}

	public static ExceptionDetail convert(FieldError error) {
		String code = HttpStatus.BAD_REQUEST.toString();
		try {
			Map<String, Object> attributes = error.unwrap(javax.validation.ConstraintViolation.class)
					.getConstraintDescriptor().getAttributes();
			if (attributes != null) {
				code = attributes.getOrDefault("businessCode", "400").toString();
			}
		}catch (Exception e){
			LOGGER.debug("mapping expeption",e);
		}
		ExceptionDetail detail = new ExceptionDetail(code, error.getDefaultMessage(), Optional.of(error.getField()));
		detail.setMetaData("rejectedValue", error.getRejectedValue());
		detail.setMetaData("field", error.getField());
		detail.setMetaData("reason", error.getCode());
		fillI18nWarnings(error, detail);
		return detail;
	}

	private static void fillI18nWarnings(FieldError error, ExceptionDetail detail) {
		String errorStr = error.getDefaultMessage();
		try {
			errorStr= error.unwrap(ConstraintViolation.class).getMessageTemplate();
		}catch (Exception e){
			LOGGER.debug("mapping expeption",e);
		}
		fillI18nWarnings(detail, errorStr);
	}

	private static void fillI18nWarnings(ExceptionDetail detail, String templateMessage) {
		if (templateMessage != null && !templateMessage.startsWith("{")) {
			detail.setMetaData(I18N_KEY, TEMPLATE_FORMAT_INCORRECT);
		} else if (templateMessage != null && templateMessage.equalsIgnoreCase(detail.getErrorMessage())) {
			detail.setMetaData(I18N_KEY, TEMPLATE_NOT_FOUND);
		}
	}

	public static ExceptionDetail internacionalizate(ExceptionDetail detail, MessageSource messageSource, Locale locale) {
		String m = detail.getErrorMessage();
		if (m.startsWith("{")) {
			Object[] args = detail.getMessageArgs();
			detail.setErrorMessage(messageSource.getMessage(StringUtils.substringBetween(m, "{", "}"), args, m, locale));
			if (detail.getErrorMessage().equalsIgnoreCase(m)) {
				detail.setMetaData(I18N_KEY, TEMPLATE_NOT_FOUND);
			}
		} else {
			detail.setMetaData(I18N_KEY, TEMPLATE_FORMAT_INCORRECT);
		}
		return detail;
	}
}
