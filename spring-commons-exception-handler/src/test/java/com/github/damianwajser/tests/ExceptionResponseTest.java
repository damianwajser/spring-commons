package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import com.github.damianwajser.model.CustomValidationFooObject;
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

import java.util.Collections;

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
			Assert.assertEquals("500", ErrorMessage.getInstance(e.getResponseBodyAsString()).getDetails().get(0).getErrorCode());
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
			Assert.assertEquals("403", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
			Assert.assertEquals("permissionDenied", ErrorMessage.getInstance(e).getDetails().get(0).getErrorMessage());
		}

	}

	@Test
	public void forbbiden() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/forbbiden", HttpMethod.POST, null, Object.class);
			Assert.fail();
		} catch (Forbidden e) {
			Assert.assertEquals("forbbiden", ErrorMessage.getInstance(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("403", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequest() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest", HttpMethod.POST, null, Object.class);
			Assert.fail();
		} catch (BadRequest e) {
			Assert.assertEquals("badrequest", ErrorMessage.getInstance(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("400", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequest_message_args() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "en-EN");
		HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(null, headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/message/Variable", HttpMethod.POST, entity, Object.class);
			Assert.fail();
		} catch (BadRequest e) {
			ExceptionDetail exceptionDetail = ErrorMessage.getInstance(e).getDetails().get(0);
			String errorMessage = exceptionDetail.getErrorMessage();
			Assert.assertEquals("400", exceptionDetail.getErrorCode());
			Assert.assertTrue(errorMessage.contains("English message with Variable on "));
			Assert.assertTrue(errorMessage.contains(" and final comment, for testing purpose as arguments"));
		}
	}

	@Test
	public void badRequest_enum() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/enum/error", HttpMethod.POST, null, Object.class);
			Assert.fail();
		} catch (BadRequest e) {
			Assert.assertEquals("No enum constant com.github.damianwajser.validator.constraint.enums.values.Countries.error", ErrorMessage.getInstance(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("400", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequest_enum_object() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject json = new JSONObject();
		json.put("a", "asd");
		json.put("b", "asd");
		HttpEntity<String> body = new HttpEntity<>(json.toString(), headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/enum", HttpMethod.POST, body, Object.class);
			Assert.fail("Expected an BadRequest to be thrown");
		} catch (BadRequest e) {
			Assert.assertEquals("Cannot deserialize value of type `com.github.damianwajser.model.EnumModel$TEST` from String \"asd\": not one of the values accepted for Enum class: [A, B]", ErrorMessage.getInstance(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("400", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequest_enum_object_array() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> body = new HttpEntity<>(null, headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/enum_array_in_object?country=A", HttpMethod.GET, body, Object.class);
			Assert.fail("Expected an BadRequest to be thrown");
		} catch (BadRequest e) {
			//Assert.assertEquals("Cannot deserialize value of type `com.github.damianwajser.model.EnumModel$TEST` from String \"asd\": not one of the values accepted for Enum class: [A, B]", TestUtils.getMessage(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("code_333", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void badRequestWithParameter() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/badrequest/1", HttpMethod.GET, null, Object.class);
			Assert.fail("Expected an BadRequest to be thrown");
		} catch (BadRequest e) {
			Assert.assertEquals("as-400", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
		}
	}

	@Test
	public void notfound() throws Exception {
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/notfound", HttpMethod.POST, null, Object.class);
		} catch (NotFound e) {
			Assert.assertEquals("notfound", ErrorMessage.getInstance(e).getDetails().get(0).getErrorMessage());
			Assert.assertEquals("404", ErrorMessage.getInstance(e).getDetails().get(0).getErrorCode());
		}
	}

}
