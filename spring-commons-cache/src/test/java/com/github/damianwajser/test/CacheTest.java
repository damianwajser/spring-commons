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
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import redis.embedded.RedisServer;

@Import({RedisConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CacheTest {

	@Autowired
	private RedisProperties redisProperties;

	private RedisServer redisServer;

	private RestTemplate restTemplate = new RestTemplate();

	@LocalServerPort
	private int port;

	@Before
	public void setUp() {
		this.redisServer = new RedisServer(redisProperties.getPort());
		redisServer.start();
	}

	@Test
	public void getCacheOK() throws Exception {
		String result = this.restTemplate
				.exchange("http://localhost:" + port + "/cache", HttpMethod.GET, null, String.class).getBody();
		String result2 = this.restTemplate
				.exchange("http://localhost:" + port + "/cache", HttpMethod.GET, null, String.class).getBody();
		Assert.assertEquals(result, result2);
	}

	@Test
	public void getCacheAllOK() throws Exception {
		Object result = this.restTemplate
				.exchange("http://localhost:" + port + "/cache_all", HttpMethod.GET, null, Object.class).getBody();

		Integer result1 = this.restTemplate
				.exchange("http://localhost:" + port + "/cache_all/1", HttpMethod.GET, null, Integer.class).getBody();
		Assert.assertEquals(result1, new Integer(1));
		Integer result2 = this.restTemplate
				.exchange("http://localhost:" + port + "/cache_all/2", HttpMethod.GET, null, Integer.class).getBody();
		Assert.assertEquals(result2, new Integer(2));
		this.restTemplate
				.exchange("http://localhost:" + port + "/cache_all/2", HttpMethod.DELETE, null, Integer.class).getBody();
		this.restTemplate
				.exchange("http://localhost:" + port + "/cache_all/2", HttpMethod.GET, null, Integer.class).getBody();
	}

	@Test
	public void getCacheEvict() throws Exception {
		String result = this.restTemplate
				.exchange("http://localhost:" + port + "/cache", HttpMethod.GET, null, String.class).getBody();
		this.restTemplate
				.exchange("http://localhost:" + port + "/cache", HttpMethod.DELETE, null, String.class).getBody();

		String result2 = this.restTemplate
				.exchange("http://localhost:" + port + "/cache", HttpMethod.GET, null, String.class).getBody();
		Assert.assertNotEquals(result, result2);
	}

	@After
	public void close() {
		redisServer.stop();
	}

}
