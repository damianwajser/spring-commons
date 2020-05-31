package com.github.damianwajser.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionResponseTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void permissionDenied() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/permissionDenied", HttpMethod.POST, null,
					Object.class);
		} catch (Forbidden e) {
			Assert.assertEquals("permissionDenied", getMessage(e));
		}

	}

	@Test
	public void forbbiden() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/forbbiden", HttpMethod.POST, null, Object.class);
		} catch (Forbidden e) {
			Assert.assertEquals("forbbiden", getMessage(e));
		}
	}

	@Test
	public void badRequest() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest", HttpMethod.POST, null, Object.class);
		} catch (BadRequest e) {
			Assert.assertEquals("badrequest", getMessage(e));
		}
	}

	@Test
	public void notfound() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/notfound", HttpMethod.POST, null, Object.class);
		} catch (NotFound e) {
			Assert.assertEquals("notfound", getMessage(e));
		}
	}

	private Object getMessage(HttpClientErrorException e) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map message = (mapper.readValue(e.getResponseBodyAsString(), Map.class));
		return ((Map) ((List<?>) message.get("details")).get(0)).get("errorMessage");
	}
}
