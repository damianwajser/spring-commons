package com.github.damianwajser.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.springframework.web.client.HttpClientErrorException;

public class TestUtils {

	public static ErrorMessage getMessage(HttpClientErrorException e) throws JsonProcessingException {
		return getMessage(e.getResponseBodyAsString());
	}

	public static ErrorMessage getMessage(String body) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper()
				.registerModule(new Jdk8Module())
				.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
				.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
				.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
		ErrorMessage message = objectMapper.readValue(body, ErrorMessage.class);
		System.out.println(body);
		return message;
	}

	public static ExceptionDetail getDetail(HttpClientErrorException e, String code) throws JsonProcessingException {
		ErrorMessage message = getMessage(e);
		return message.getDetails().stream().filter((detail) -> detail.getErrorCode().equalsIgnoreCase(code)).findAny().get();
	}
}
