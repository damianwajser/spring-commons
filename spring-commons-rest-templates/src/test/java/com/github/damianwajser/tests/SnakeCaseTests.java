package com.github.damianwajser.tests;

import com.github.damianwajser.model.snake.LocalDateObject;
import com.github.damianwajser.model.snake.RequestToController;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class SnakeCaseTests {
	@LocalServerPort
	private int port;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("snake_template")
	private RestTemplate restTemplateSnake;

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
		assertEquals(jsonCamel.toString(), response.get("data"));
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
		assertEquals(jsonSnake.toString(), response.get("data"));
		System.out.println(response);
	}

	@Test
	public void testCastLocalDate() {
		RequestToController request = new RequestToController();
		request.setSomeValue("1986-05-17");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity(request, headers);

		LocalDateObject response = this.restTemplateSnake
				.postForEntity("http://localhost:" + port + "/snake_case/local_date", requestEntity, LocalDateObject.class).getBody();
		assertEquals(LocalDate.of(1986, 5, 17), response.getSomeValue());
		System.out.println(response);
	}
}
