package com.github.damianwajser.tests.global.e2e;

import com.github.damianwajser.model.especifics.NoEmptyObjectOnlyPut;
import com.github.damianwajser.model.global.NoEmptyObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class NotEmptyValidationTest {

	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test(expected = BadRequest.class)
	public void notempty() throws Exception {
		this.restTemplate.postForEntity("http://localhost:" + port + "/badrequest", new NoEmptyObject(""), Object.class);
	}

	@Test
	public void notempty_exclude() throws Exception {
		HttpStatus statusCode = this.restTemplate.exchange("http://localhost:" + port + "/badrequest", HttpMethod.PUT,
				new HttpEntity<>(new NoEmptyObject("")), Object.class).getStatusCode();
		assertThat(statusCode).isEqualTo(HttpStatus.OK);
	}

	@Test(expected = BadRequest.class)
	public void only_put_test() throws Exception {
		HttpStatus statusCode = this.restTemplate.exchange("http://localhost:" + port + "/badrequest_only_put", HttpMethod.PUT,
				new HttpEntity<>(new NoEmptyObjectOnlyPut("")), Object.class).getStatusCode();
	}

	@Test
	public void only_put_test_ok() throws Exception {
		HttpStatus statusCode = this.restTemplate.exchange("http://localhost:" + port + "/badrequest_only_put", HttpMethod.POST,
				new HttpEntity<>(new NoEmptyObjectOnlyPut("")), Object.class).getStatusCode();
		assertThat(statusCode).isEqualTo(HttpStatus.OK);
	}
}
