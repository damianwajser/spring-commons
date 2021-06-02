package com.github.damianwajser.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestingWebApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testOK() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Client-Id", "3");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		Map<String, Map<String, String>> response = this.restTemplate
				.exchange("http://localhost:" + port + "/replayheaders", HttpMethod.GET, entity, Map.class).getBody();
		assertThat(response.get("headers").get("X-Client-Id")).contains("3");
	}

	@Test
	public void testPATCH_OK() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Client-Id", "3");

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		Map<String, String> response = this.restTemplate
				.exchange("http://localhost:" + port + "/patch_test", HttpMethod.PATCH, entity, Map.class).getBody();

		assertThat(response.get("some_value")).isEqualTo("patch");
	}

	@Test
	public void testNOK() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Client-Id", "4");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		Map<String, Map<String, String>> response = this.restTemplate
				.exchange("http://localhost:" + port + "/replayheaders", HttpMethod.GET, entity, Map.class).getBody();
		assertThat(response.get("headers").get("X-Client-Id")).doesNotContain("3");
	}

	@Test
	public void testOKRepiteHeader() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Client-Id", "4");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		Map<String, Map<String, String>> response = this.restTemplate
				.exchange("http://localhost:" + port + "/replayheaders_add_4", HttpMethod.GET, entity, Map.class).getBody();
		assertThat(response.get("headers").get("X-Client-Id")).isEqualTo("4");
	}
}
