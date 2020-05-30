package com.github.damianwajser.tests;

import com.github.damianwajser.configuration.IdempotencyConfiguration;
import com.github.damianwajser.configuration.RedisConfiguration;
import com.github.damianwajser.controllers.IdempotencyController;
import com.github.damianwajser.idempotency.configuration.IdempotencyEndpoints;
import com.github.damianwajser.idempotency.configuration.IdempotencyProperties;
import com.github.damianwajser.idempotency.filters.IdempontecyFilter;
import com.github.damianwajser.model.FooObject;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import redis.embedded.RedisServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@Import({RedisConfiguration.class, IdempotencyConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IdempotencyTest {

	private static String HEADER_IDEM = "X-Idempotency-Key";

	private RedisServer redisServer;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RedisProperties redisProperties;

	private RestTemplate restTemplate = new RestTemplate();

	@LocalServerPort
	private int port;

	@After
	public void close() {
		redisServer.stop();
	}

	@Before
	public void setUp() {
		this.redisServer = new RedisServer(redisProperties.getPort());
		redisServer.start();
	}

	@Test(expected = IllegalArgumentException.class)
	public void notEmptyHeadersInitializer() {
		new IdempontecyFilter(redisTemplate, new IdempotencyEndpoints(), new IdempotencyProperties());
	}

	@Test
	public void testMockRedis() {
		redisTemplate.opsForValue().set("key", "algo");
		Assert.assertEquals("algo", redisTemplate.opsForValue().get("key"));
	}

	@Test(expected = HttpClientErrorException.BadRequest.class)
	public void nonHeaders_badRequest() throws Exception {
		String url = "http://localhost:" + port + "/idempotency";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		sendIdempotency(entity, url);
	}

	@Test()
	public void conflict() throws Exception {
		String idemKey = "conflict";
		String url = "http://localhost:" + port + "/idempotency_delay";
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_IDEM, idemKey);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ExecutorService service = Executors.newFixedThreadPool(4);
		Future<FooObject> future = service.submit(() -> sendIdempotency(entity, url));
		Thread.sleep(1000);
		Future<FooObject> future2 = service.submit(() -> sendIdempotency(entity, url));
		Assert.assertEquals(future.get().getValue(), IdempotencyController.value);
		try {
			future2.get();
			Assert.fail("not work");
		} catch (ExecutionException e) {
			HttpClientErrorException.Conflict conflict = (HttpClientErrorException.Conflict) e.getCause();
			assertThat(conflict.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		}
		Assert.assertEquals(sendIdempotency(entity, url).getValue(), IdempotencyController.value);
	}

	public FooObject sendIdempotency(HttpEntity entity, String url) {

		FooObject result = this.restTemplate
				.exchange(url, HttpMethod.POST, entity, FooObject.class).getBody();
		// Artificial delay of 1s for demonstration purposes
		return result;
	}

	@Test
	public void getPostOK() throws Exception {
		String idemKey = "getPostOK";
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_IDEM, idemKey);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		FooObject result = this.restTemplate
				.exchange("http://localhost:" + port + "/idempotency", HttpMethod.POST, entity, FooObject.class).getBody();
		Assert.assertEquals(result.getValue(), IdempotencyController.value);
		FooObject result2 = this.restTemplate
				.exchange("http://localhost:" + port + "/idempotency", HttpMethod.POST, entity, FooObject.class).getBody();
		Assert.assertEquals(result, result2);
	}

	@Test
	public void getPostBadRequest() throws Exception {
		String idemKey = "getPostBadRequest";
		HttpHeaders headers = new HttpHeaders();
		headers.set(HEADER_IDEM, idemKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String body1 = "";
		String body2 = "";
		HttpStatus status1 = null;
		HttpStatus status2 = null;

		try {
			this.restTemplate
					.exchange("http://localhost:" + port + "/idempotency_bad_request", HttpMethod.POST, entity, FooObject.class);
			Assert.fail("conflict");
		} catch (HttpClientErrorException.BadRequest e) {
			body1 = e.getResponseBodyAsString();
			status1 = e.getStatusCode();
		}
		try {
			this.restTemplate
					.exchange("http://localhost:" + port + "/idempotency_bad_request", HttpMethod.POST, entity, FooObject.class);
			Assert.fail("conflict");
		} catch (HttpClientErrorException.BadRequest e) {
			body2 = e.getResponseBodyAsString();
			status2 = e.getStatusCode();
		}
		assertThat(body1).isEqualTo(body2);
		assertThat(status1).isEqualTo(status2);
	}
}
