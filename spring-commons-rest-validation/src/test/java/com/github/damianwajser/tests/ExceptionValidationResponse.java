package com.github.damianwajser.tests;

import com.github.damianwajser.model.FooObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionValidationResponse {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test(expected = BadRequest.class)
	public void notempty() throws Exception {
		this.restTemplate.postForEntity("http://localhost:" + port + "/badrequest", new FooObject(""), Object.class);
	}

	@Test
	public void notempty_exclude() throws Exception {
		this.restTemplate.exchange("http://localhost:" + port + "/badrequest", HttpMethod.PUT,
				new HttpEntity<FooObject>(new FooObject("")), Object.class);
	}


}
