package com.github.damianwajser.test;

import com.github.damianwajser.controller.ServiceCacheTtl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServer;

@Import({ServiceCacheTtl.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TtlTest {

	@Autowired
	private ServiceCacheTtl service;

	private RedisServer redisServer;

	@Autowired
	private RedisProperties redisProperties;

	@Before
	public void setUp() {
		this.redisServer = new RedisServer(redisProperties.getPort());
		redisServer.start();
	}

	@After
	public void close() {
		redisServer.stop();
	}

	@Test
	public void testTTL_2_sec() throws InterruptedException {
		String uuid1 = service.generateUUID();
		Assert.assertEquals(uuid1, service.generateUUID());
		Thread.sleep(2000);
		Assert.assertNotEquals(uuid1, service.generateUUID());
	}

	@Test
	public void testTTL_5_sec() throws InterruptedException {
		String uuid1 = service.generateUUID2();
		Assert.assertEquals(uuid1, service.generateUUID2());
		Thread.sleep(2000);
		Assert.assertEquals(uuid1, service.generateUUID2());
		Thread.sleep(1000);
		Assert.assertNotEquals(uuid1, service.generateUUID2());
	}

	@Test
	public void testTTL_default_sec() throws InterruptedException {
		String uuid1 = service.generateUUID3();
		Assert.assertEquals(uuid1, service.generateUUID3());
		Thread.sleep(1000);
		Assert.assertEquals(uuid1, service.generateUUID3());
		Thread.sleep(1000);
		Assert.assertNotEquals(uuid1, service.generateUUID3());
	}
}
