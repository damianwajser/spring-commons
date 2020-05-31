package com.github.damianwajser.idempotency.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

	public String objectToJsonString(Object message) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		String objectJson = objectMapper.writeValueAsString(message)
				.replaceAll("\\\\", "");
		if (objectJson.startsWith("\"")) {
			objectJson = objectJson.replaceFirst("\"", "").trim();
		}

		if (objectJson.substring(objectJson.length() - 1, objectJson.length()).equals("\"")) {
			objectJson = objectJson.substring(0, objectJson.length() - 1);
		}
		LOGGER.info("convert json: {}", objectJson);
		return objectJson;
	}
}
