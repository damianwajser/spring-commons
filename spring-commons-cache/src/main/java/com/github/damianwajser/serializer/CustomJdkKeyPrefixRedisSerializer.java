package com.github.damianwajser.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CustomJdkKeyPrefixRedisSerializer extends JdkSerializationRedisSerializer {

	private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	private final String PREFIX;

	public CustomJdkKeyPrefixRedisSerializer(String prefix) {
		PREFIX = prefix;
	}

	@Override
	public byte[] serialize(Object source) {
		if (source instanceof String || source instanceof Number) {
			String key = PREFIX + source.toString();
			return stringRedisSerializer.serialize(key);
		}
		return super.serialize(source);
	}

	@Override
	public Object deserialize(byte[] source) {
		String saveKey = stringRedisSerializer.deserialize(source);
		int indexOf = saveKey.indexOf(PREFIX);
		if (indexOf > 0) {
			return null;
		} else {
			return saveKey.substring(indexOf);
		}
	}

}
