package com.github.damianwajser.tests.ok.http;

import com.github.damianwajser.model.snake.SingletonObject;
import com.github.damianwajser.model.timeout.TimeOutObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"spring.commons.rest.template.timeout.write=300",
		"spring.commons.rest.template.timeout.read=300",
		"spring.commons.rest.template.implementation=OK_HTTP"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeOutTests {
	@LocalServerPort
	private int port;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void testOK() throws Exception {
		SingletonObject.getInstance().restart();
		LocalDateTime dataTime = LocalDateTime.now();
		TimeOutObject request = new TimeOutObject();
		request.setStart(new Date().getTime());
		System.out.println("start request: " + request.getStart());
		try {
			this.restTemplate
					.postForObject("http://localhost:" + port + "/timeout", request, TimeOutObject.class);
			Assert.fail("");
		} catch (ResourceAccessException e) {
			System.out.println("TimeOut- start new delay: " + Duration.between(dataTime, LocalDateTime.now()).toMillis());
			e.printStackTrace();
			Thread.sleep(400);
			System.out.println("TimeOut- stop new delay: " + Duration.between(dataTime, LocalDateTime.now()).toMillis());
			Integer response = this.restTemplate
					.getForObject("http://localhost:" + port + "/without_timeout", Integer.class);
			Assert.assertEquals(1, response.intValue());
		}
	}
}
