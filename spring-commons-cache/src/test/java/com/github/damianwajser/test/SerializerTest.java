package com.github.damianwajser.test;

import com.github.damianwajser.serializer.CustomJdkKeyPrefixRedisSerializer;
import com.github.damianwajser.serializer.CustomJdkRedisSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
public class SerializerTest {


	@Test
	public void serializePrefixOK() throws Exception {
		CustomJdkKeyPrefixRedisSerializer serializer = new CustomJdkKeyPrefixRedisSerializer("ms-test");
		byte[] serial = serializer.serialize("hola");
		Assert.assertEquals("hola", serializer.deserialize(serial));
		byte[] serial2 = serializer.serialize(1);
		Assert.assertEquals("1", serializer.deserialize(serial2));
		byte[] serial3 = serializer.serialize(Arrays.asList("hola"));
		Assert.assertEquals(Arrays.asList("hola").toString(), serializer.deserialize(serial3));
	}

	@Test
	public void serializeWithoutPrefixOK() throws Exception {
		CustomJdkRedisSerializer serializer = new CustomJdkRedisSerializer();
		byte[] serial = serializer.serialize("hola");
		Assert.assertEquals("hola", serializer.deserialize(serial));
		byte[] serial2 = serializer.serialize(1);
		Assert.assertEquals("1", serializer.deserialize(serial2));
		byte[] serial3 = serializer.serialize(Arrays.asList("hola"));
		Assert.assertEquals(Arrays.asList("hola"), serializer.deserialize(serial3));
	}
}
