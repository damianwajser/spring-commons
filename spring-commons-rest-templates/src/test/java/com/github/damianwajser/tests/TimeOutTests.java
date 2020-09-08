package com.github.damianwajser.tests;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeOutTests {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

	@Test
	public void testOK() throws Exception {
		try {
			System.out.println("start request");
			this.restTemplate
					.getForObject("http://localhost:" + port + "/timeout", Integer.class);
			Assert.fail();
		} catch (ResourceAccessException e) {
			System.out.println("TimeOut- start new delay");
			Thread.sleep(200);
			System.out.println("TimeOut- stop new delay");
			Integer response = this.restTemplate
					.getForObject("http://localhost:" + port + "/without_timeout", Integer.class);
			Assert.assertEquals(1, response.intValue());
		}

	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		final RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(50)
				.setConnectionRequestTimeout(50)
				.setSocketTimeout(50)
				.build();
		final CloseableHttpClient client = HttpClientBuilder
				.create()
				.setDefaultRequestConfig(config)
				.build();

		return new HttpComponentsClientHttpRequestFactory(client);
	}
}
