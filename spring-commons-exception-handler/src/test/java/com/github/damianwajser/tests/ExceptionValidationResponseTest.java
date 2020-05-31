package com.github.damianwajser.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damianwajser.model.FooObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionValidationResponseTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void badrequest() throws Exception {
		try {
			this.restTemplate.postForEntity("http://localhost:" + port + "/validation/badrequest", new FooObject(""),
					Object.class);
		} catch (BadRequest e) {
			Assert.assertEquals("400", getMessage(e, "errorCode"));
			// Assert.assertEquals(getMessage(e, "errorDetail"), "value");
		}
	}

	private Object getMessage(HttpClientErrorException e, String field)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map message = (mapper.readValue(e.getResponseBodyAsString(), Map.class));
		return ((Map) ((List<?>) message.get("details")).get(0)).get(field);
	}
}
