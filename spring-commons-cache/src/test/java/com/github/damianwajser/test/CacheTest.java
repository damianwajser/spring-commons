package com.primamp.todopago;

import com.prismamp.todopago.configuration.RedisProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

	@Autowired
	private RestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Before
	public void setUp() {
		this.redisServer = new RedisServer(redisProperties.getRedisPort());
		redisServer.start();
	}

	@Test
	public void getCacheOK() throws Exception {
		String idemKey = "getPostOK";
		HttpHeaders headers = new HttpHeaders();
		//headers.set(HEADER_IDEM, idemKey);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String result = this.restTemplate
				.exchange("http://localhost:" + port + "/cache", HttpMethod.POST, entity, String.class).getBody();
		String result2 = this.restTemplate
				.exchange("http://localhost:" + port + "/cache", HttpMethod.POST, entity, String.class).getBody();
		Assert.assertEquals(result, result2);
	}

}
