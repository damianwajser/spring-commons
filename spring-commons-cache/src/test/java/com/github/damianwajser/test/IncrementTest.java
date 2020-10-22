package com.github.damianwajser.test;

import com.github.damianwajser.configuration.RedisConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServer;

@Import({RedisConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IncrementTest {

	@Autowired
	private RedisProperties redisProperties;

	private RedisServer redisServer;

	@Autowired
	private RedisTemplate redisTemplate;

	@Before
	public void setUp() {
		this.redisServer = new RedisServer(redisProperties.getPort());
		redisServer.start();
	}

	@Test
	public void IncrementOk() throws Exception {
		redisTemplate.opsForValue().set("key", 1);
		redisTemplate.opsForValue().increment("key", 3);
		int value = Integer.parseInt(redisTemplate.opsForValue().get("key").toString());
		Assert.assertEquals(4, value);

		redisTemplate.opsForValue().increment("key1", 4);
		int value1 = Integer.parseInt(redisTemplate.opsForValue().get("key1").toString());
		Assert.assertEquals(4, value1);


	}

	@Test
	public void IncrementMoreCommplexity() throws Exception {
		int one = 1;
		int two = 3;
		double three = 123.23452344365;
		double four = 3.1;
		double five = 3.8523432422342343212;
		double result = one + two + three + four + five;

		redisTemplate.opsForValue().set("key", one);
		redisTemplate.opsForValue().increment("key", two);

		redisTemplate.opsForValue().increment("key", three);
		redisTemplate.opsForValue().increment("key", four);
		redisTemplate.opsForValue().increment("key", five);

		double value = Double.parseDouble(redisTemplate.opsForValue().get("key").toString());
		Assert.assertEquals(result, value, 0);


	}

	@Test
	public void decrementOk() throws Exception {
		redisTemplate.opsForValue().set("key", 3);
		redisTemplate.opsForValue().decrement("key", 1);
		int value = Integer.parseInt(redisTemplate.opsForValue().get("key").toString());
		Assert.assertEquals(2, value);

		redisTemplate.opsForValue().decrement("key1", 3);
		int value1 = Integer.parseInt(redisTemplate.opsForValue().get("key1").toString());
		Assert.assertEquals(-3, value1);


	}

	@After
	public void close() {
		redisServer.stop();
	}

}
