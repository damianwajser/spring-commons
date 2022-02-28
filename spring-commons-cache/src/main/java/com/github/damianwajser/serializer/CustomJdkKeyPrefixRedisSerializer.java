package com.github.damianwajser.serializer;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CustomJdkKeyPrefixRedisSerializer extends JdkSerializationRedisSerializer {

	private final String prefix;
	private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	public CustomJdkKeyPrefixRedisSerializer(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public byte[] serialize(Object source) {
		String key = prefix + source.toString();
		return stringRedisSerializer.serialize(key);
	}

	@Override
	public Object deserialize(byte[] source) {
		String saveKey = stringRedisSerializer.deserialize(source);
		int indexOf = saveKey.indexOf(prefix);
		if (indexOf > 0) {
			return null;
		} else {
			return saveKey.substring(indexOf + prefix.length());
		}
	}

}
