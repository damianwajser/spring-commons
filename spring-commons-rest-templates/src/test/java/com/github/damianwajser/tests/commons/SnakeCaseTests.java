package com.github.damianwajser.tests.commons;

import com.github.damianwajser.tests.AbstractHttpTest;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class SnakeCaseTests extends AbstractHttpTest {
	@LocalServerPort
	private int port;
	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testOK() throws Exception {
		JSONObject jsonSnake = new JSONObject();
		jsonSnake.put("some_value", "value");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(jsonSnake.toString(), headers);

		Map<String, String> response = this.restTemplate
				.postForEntity("http://localhost:" + port + "/camel_case", request, Map.class).getBody();
		JSONObject jsonCamel = new JSONObject();
		jsonCamel.put("someValue", "value");
		Assert.assertEquals(jsonCamel.toString(), response.get("data"));
		System.out.println(response);
	}

	@Test
	public void test() throws Exception {
		JSONObject jsonSnake = new JSONObject();
		jsonSnake.put("some_value", "value");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(jsonSnake.toString(), headers);

		Map<String, String> response = this.restTemplate
				.postForEntity("http://localhost:" + port + "/snake_case", request, Map.class).getBody();
		Assert.assertEquals(jsonSnake.toString(), response.get("data"));
		System.out.println(response);
	}
}
