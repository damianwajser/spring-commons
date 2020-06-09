package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.handlers.FieldErrorMapper;
import com.github.damianwajser.model.CustomValidationFooObject;
import com.github.damianwajser.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomValidationI18nTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();


	@Test
	public void withDefaultMessage() throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("Accept-Language", "en-EN");
			HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(new CustomValidationFooObject("", ""), headers);
			this.restTemplate.exchange("http://localhost:" + port + "/validation/custom", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("defaultCode", TestUtils.getDetail(e, "defaultCode").getErrorCode());
			Assert.assertEquals("must not be empty", TestUtils.getDetail(e, "defaultCode").getErrorMessage());

			Assert.assertEquals("Engilsh message",  TestUtils.getDetail(e, "customCode").getErrorMessage());
			Assert.assertEquals("customCode", TestUtils.getDetail(e, "customCode").getErrorCode());

			Assert.assertEquals("message", TestUtils.getDetail(e, "customStringCode").getErrorMessage());
			Assert.assertEquals("customStringCode", TestUtils.getDetail(e, "customStringCode").getErrorCode());
			Assert.assertEquals(FieldErrorMapper.TEMPLATE_FORMAT_INCORRECT, TestUtils.getDetail(e, "customStringCode").getMetaData().get("i18n"));

			Assert.assertEquals("{spring.commons}", TestUtils.getDetail(e, "noStringMessage").getErrorMessage());
			Assert.assertEquals("noStringMessage", TestUtils.getDetail(e, "noStringMessage").getErrorCode());
			Assert.assertEquals(FieldErrorMapper.TEMPLATE_NOT_FOUND, TestUtils.getDetail(e, "noStringMessage").getMetaData().get("i18n"));
		}
	}

	@Test
	public void withI18NMessage_Spanish() throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("Accept-Language", "es-ES");
			HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(new CustomValidationFooObject("", ""), headers);
			this.restTemplate.exchange("http://localhost:" + port + "/validation/custom", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("defaultCode", TestUtils.getDetail(e, "defaultCode").getErrorCode());
			Assert.assertEquals("no puede estar vacío", TestUtils.getDetail(e, "defaultCode").getErrorMessage());

			Assert.assertEquals("customCode", TestUtils.getDetail(e, "customCode").getErrorCode());
			Assert.assertEquals("Español message",  TestUtils.getDetail(e, "customCode").getErrorMessage());

			Assert.assertEquals("{spring.commons}", TestUtils.getDetail(e, "noStringMessage").getErrorMessage());
			Assert.assertEquals("message", TestUtils.getDetail(e, "customStringCode").getErrorMessage());
		}
	}
	@Test
	public void withI18NMessage_French() throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("Accept-Language", "fr-FR");
			HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(new CustomValidationFooObject("", ""), headers);
			this.restTemplate.exchange("http://localhost:" + port + "/validation/custom", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("defaultCode", TestUtils.getDetail(e, "defaultCode").getErrorCode());
			Assert.assertEquals("ne peut pas être vide", TestUtils.getDetail(e, "defaultCode").getErrorMessage());

			Assert.assertEquals("customCode", TestUtils.getDetail(e, "customCode").getErrorCode());
			Assert.assertEquals("French message",  TestUtils.getDetail(e, "customCode").getErrorMessage());

			Assert.assertEquals("{spring.commons}", TestUtils.getDetail(e, "noStringMessage").getErrorMessage());
			Assert.assertEquals("message", TestUtils.getDetail(e, "customStringCode").getErrorMessage());
		}
	}
}
