package com.github.damianwajser.tests.ok.http;

import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.commons.rest.template.implementation=OK_HTTP"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultTimeOutTests {
	@LocalServerPort
	private int port;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testOK() {
		System.out.println("start request without delay");
		final Integer result = this.restTemplate.getForObject("http://localhost:" + port + "/without_timeout", Integer.class);
		System.out.println("end request without delay");

		assertThat(result, notNullValue());
		assertThat(restTemplate.getRequestFactory(), instanceOf(InterceptingClientHttpRequestFactory.class));
	}
}
