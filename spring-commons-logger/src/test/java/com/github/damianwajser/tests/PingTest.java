package com.github.damianwajser.tests;

import com.github.damianwajser.configuration.PropertiesLogger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingTest {

	@LocalServerPort
	private int port;

	@Autowired
	private PropertiesLogger propertiesLogger;

	@Value("${spring.commons.logger.trace.id}")
	private String traceKey;

	@Value("${spring.commons.logger.app.name}")
	private String appName;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void contextLoad() throws Exception {
		String result = this.restTemplate.getForEntity("http://localhost:" + port + "/ping", String.class).getBody();
		Assert.assertThat(result, Matchers.equalTo("pong"));
		Assert.assertEquals(appName, propertiesLogger.getPropetiesToShow().get(PropertiesLogger.APP_NAME));
		//two twice for if (isEmpty)
		Assert.assertEquals(appName, propertiesLogger.getPropetiesToShow().get(PropertiesLogger.APP_NAME));
	}

	@Test
	public void contextLoadKey() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.put(traceKey, Arrays.asList("key"));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/ping", HttpMethod.GET, request, String.class);
		Assert.assertThat(response.getBody(), Matchers.equalTo("pong"));
	}
}
