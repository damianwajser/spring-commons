package com.prismamp.todopago.serializer;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CustomJackson2JasonRedisSerializer extends Jackson2JsonRedisSerializer<Object> {
	private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	public CustomJackson2JasonRedisSerializer() {
		super(Object.class);
	}

	@Override
	public byte[] serialize(Object source) throws SerializationException {
		if(source instanceof String || source instanceof Number) {
			return stringRedisSerializer.serialize(source.toString());
		}
		return super.serialize(source);
	}

	@Override
	public Object deserialize(byte[] source) throws SerializationException {
		try {
			return super.deserialize(source);
		} catch(SerializationException sex) {
			return stringRedisSerializer.deserialize(source);
		}
	}
}
