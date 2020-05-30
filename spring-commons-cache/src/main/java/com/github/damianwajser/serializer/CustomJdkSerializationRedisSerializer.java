package com.github.damianwajser.serializer;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CustomJdkSerializationRedisSerializer extends JdkSerializationRedisSerializer {
	private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	@Override
	public byte[] serialize(Object source) {
		if(source instanceof String || source instanceof Number) {
			return stringRedisSerializer.serialize(source.toString());
		}
		return super.serialize(source);
	}

	@Override
	public Object deserialize(byte[] source) {
		try {
			return super.deserialize(source);
		} catch(SerializationException sex) {
			return stringRedisSerializer.deserialize(source);
		}
	}
}
