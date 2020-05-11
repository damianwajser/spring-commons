package com.github.damianwajser.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpHeaderTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testOK() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Client-Id", "3");

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		Map<String, Map<String, String>> response = this.restTemplate
				.exchange("http://localhost:" + port + "/replayheaders", HttpMethod.GET, entity, Map.class).getBody();
		assertThat(response.get("headers").get("X-Client-Id")).contains("3");
	}

	@Test
	public void testNOK() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Client-Id", "4");

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		Map<String, Map<String, String>> response = this.restTemplate
				.exchange("http://localhost:" + port + "/replayheaders", HttpMethod.GET, entity, Map.class).getBody();
		assertThat(response.get("headers").get("X-Client-Id")).doesNotContain("3");
	}

}
