package com.github.damianwajser.tests;

import com.github.damianwajser.utils.TestUtils;
import org.json.JSONObject;
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
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionResponseTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void permissionOther() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/other", HttpMethod.POST, null,
					Object.class);
			Assert.fail();
		} catch (InternalServerError e) {
			//e.printStackTrace();
			Assert.assertEquals("500", TestUtils.getMessage(e.getResponseBodyAsString()).getDetails().get(0).getErrorCode());
			//Assert.assertEquals("permissionDenied", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
		}

	}

	@Test
	public void permissionDenied() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/permissionDenied", HttpMethod.POST, null,
					Object.class);
			Assert.fail();
		} catch (Forbidden e) {
			Assert.assertEquals("403", TestUtils.getMessage(e).getDetails().get(0).getErrorCode());
			Assert.assertEquals("permissionDenied", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
		}

	}

	@Test
	public void forbbiden() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/forbbiden", HttpMethod.POST, null, Object.class);
			Assert.fail();
		} catch (Forbidden e) {
			Assert.assertEquals("forbbiden", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("403", TestUtils.getMessage(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequest() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest", HttpMethod.POST, null, Object.class);
			Assert.fail();
		} catch (BadRequest e) {
			Assert.assertEquals("badrequest", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("400", TestUtils.getMessage(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequest_enum() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/enum/error", HttpMethod.POST, null, Object.class);
			Assert.fail();
		} catch (BadRequest e) {
			Assert.assertEquals("No enum constant com.github.damianwajser.validator.constraint.enums.values.Countries.error", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("400", TestUtils.getMessage(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequest_enum_object() throws Exception {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject json = new JSONObject();
			json.put("a", "asd");
			json.put("b", "asd");
			HttpEntity<String> body = new HttpEntity<>(json.toString(), headers);
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/enum", HttpMethod.POST, body, Object.class);
			Assert.fail();
		} catch (BadRequest e) {
			Assert.assertEquals("Cannot deserialize value of type `com.github.damianwajser.model.EnumModel$TEST` from String \"asd\": not one of the values accepted for Enum class: [A, B]", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("400", TestUtils.getMessage(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequestWithParameter() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/1", HttpMethod.GET, null, Object.class);
			Assert.fail();
		} catch (BadRequest e) {
			Assert.assertEquals("as-400", TestUtils.getMessage(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void notfound() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/notfound", HttpMethod.POST, null, Object.class);
		} catch (NotFound e) {
			Assert.assertEquals("notfound", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("404", TestUtils.getMessage(e).getDetails().get(0).getErrorCode());
		}
	}

}
