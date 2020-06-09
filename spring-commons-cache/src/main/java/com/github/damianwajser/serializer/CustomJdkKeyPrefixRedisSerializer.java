package com.github.damianwajser.serializer;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CustomJdkKeyPrefixRedisSerializer extends JdkSerializationRedisSerializer {

	private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	private final String prefix;

	public CustomJdkKeyPrefixRedisSerializer(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public byte[] serialize(Object source) {
		if (source instanceof String || source instanceof Number) {
			String key = prefix + source.toString();
			return stringRedisSerializer.serialize(key);
		}
		return super.serialize(source);
	}

	@Override
	public Object deserialize(byte[] source) {
		String saveKey = stringRedisSerializer.deserialize(source);
		int indexOf = saveKey.indexOf(prefix);
		if (indexOf > 0) {
			return null;
		} else {
			return saveKey.substring(indexOf);
		}
	}

}
