package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.model.FooObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionValidationResponseTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void badrequest() throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("Accept-Language", "en-US");
			HttpEntity<FooObject> entity = new HttpEntity<>(new FooObject(""), headers);

			this.restTemplate.exchange("http://localhost:" + port + "/validation/badrequest", HttpMethod.POST, entity,
					Object.class);
		} catch (BadRequest e) {
			Assert.assertEquals("400", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
			Assert.assertEquals("must not be empty", ErrorMessage.getInstance(e).getDetails().get(0).getErrorMessage());
		}
	}
}
