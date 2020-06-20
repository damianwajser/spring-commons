package com.github.damianwajser.tests;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingTest {

	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void contextLoad() throws Exception {
		String result = this.restTemplate.getForEntity("http://localhost:"+port+"/ping",String.class).getBody();
		Assert.assertThat(result, Matchers.equalTo("pong"));
	}
}
