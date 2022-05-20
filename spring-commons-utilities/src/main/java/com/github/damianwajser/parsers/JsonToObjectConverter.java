package com.github.damianwajser.parsers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonToObjectConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonToObjectConverter.class);
	private static Pattern pattern = Pattern.compile("(\\$\\.\\S+)", Pattern.MULTILINE);
	private Mapper mapper;

	public JsonToObjectConverter(Mapper mapper) {
		this.mapper = mapper;
	}

	public <T> T convert(String s, Class<T> clazz) throws Exception {

		return this.convert(JsonPath.parse(s), clazz);
	}

	public <T> T convert(DocumentContext json, Class<T> clazz) throws Exception {

		T result = clazz.getDeclaredConstructor(null).newInstance();
		for (Map.Entry<String, String> entry : this.mapper.getMappings().entrySet()) {
			this.setField(result, entry.getKey(), getValue(json, entry.getValue()));
		}

		return result;
	}

	private Object getValue(DocumentContext json, String fieldValue) {
		for (Map.Entry<String, String> entry : getPaths(json, fieldValue).entrySet()) {
			fieldValue = StringUtils.replace(fieldValue, entry.getKey(), entry.getValue());
		}
		return fieldValue;
	}

	private Map<String, String> getPaths(DocumentContext json, String fieldValue) {
		Matcher m = pattern.matcher(fieldValue);
		Map<String, String> jsonPaths = new HashMap<>();
		while (m.find()) {
			for (int i = 0; i < m.groupCount(); i++) {
				String path = cleanJsonPath(m.group(i));
				jsonPaths.put(path, getReplacement(json, path));
			}
		}
		return jsonPaths;
	}

	private String cleanJsonPath(String match) {
		return match.replaceAll(",$", "");
	}

	private String getReplacement(DocumentContext json, String path) {
		String replacement = StringUtils.EMPTY;
		try {
			replacement = json.read(path).toString();
		} catch (Exception e) {
			LOGGER.debug("Error with replace", e);
			if (!mapper.isSkipPathNotFound()) {
				throw e;
			}
		}
		return replacement;
	}

	private void setField(Object target, String name, Object value) {
		Assert.notNull(target, "Target object must not be null");
		Assert.notNull(name, "Target object must not be null");
		Field field = ReflectionUtils.findField(target.getClass(), name);
		Assert.notNull(field, "Could not find field [" + name + "] on target [" + target + "]");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, target, value);
	}
}
