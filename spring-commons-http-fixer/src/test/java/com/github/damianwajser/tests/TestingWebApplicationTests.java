package com.github.damianwajser.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestingWebApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testOK() throws Exception {

		assertThat(this.restTemplate
				.exchange("http://localhost:" + port + "/postok", HttpMethod.POST, null, Object.class).getStatusCode())
						.isEqualTo(HttpStatus.CREATED);
		System.err.println("termino test 1");
		assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getok", HttpMethod.GET, null, Object.class)
				.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	public void testNOK() throws Exception {

		assertThat(this.restTemplate
				.exchange("http://localhost:" + port + "/postnok", HttpMethod.POST, null, Object.class).getStatusCode())
						.isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		System.err.println("termino test 1");
		assertThat(this.restTemplate
				.exchange("http://localhost:" + port + "/getnok", HttpMethod.GET, null, Object.class).getStatusCode())
						.isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Test
	public void testChanged() throws Exception {
		assertThat(this.restTemplate
				.exchange("http://localhost:" + port + "/postchanged", HttpMethod.POST, null, Object.class)
				.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
	}
}
