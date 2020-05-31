package com.github.damianwajser.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionCodeTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test(expected = Forbidden.class)
	public void permissionDenied() throws Exception {
		this.restTemplate.exchange("http://localhost:" + port + "/permissionDenied", HttpMethod.POST, null,
				Object.class);

	}

	@Test(expected = Forbidden.class)
	public void forbbiden() throws Exception {
		this.restTemplate.exchange("http://localhost:" + port + "/forbbiden", HttpMethod.POST, null, Object.class);
	}

	@Test(expected = HttpClientErrorException.Conflict.class)
	public void conflict() throws Exception {
		this.restTemplate.exchange("http://localhost:" + port + "/conflict", HttpMethod.POST, null, Object.class);
	}

	@Test(expected = BadRequest.class)
	public void badRequest() throws Exception {
		this.restTemplate.exchange("http://localhost:" + port + "/badrequest", HttpMethod.POST, null, Object.class);
	}

	@Test(expected = NotFound.class)
	public void notfound() throws Exception {
		this.restTemplate.exchange("http://localhost:" + port + "/notfound", HttpMethod.POST, null, Object.class);
	}
}
