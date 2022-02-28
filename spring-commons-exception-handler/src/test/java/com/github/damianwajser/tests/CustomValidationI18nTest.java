package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.handlers.ExceptionDetailMapper;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import com.github.damianwajser.model.CustomValidationFooObject;
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
	private final RestTemplate restTemplate = new RestTemplate();
	@LocalServerPort
	private int port;

	@Test
	public void withDefaultMessage() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "en-EN");
		HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(new CustomValidationFooObject("", ""), headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/validation/custom", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("defaultCode", ExceptionDetail.getDetail(e, "defaultCode").getErrorCode());
			Assert.assertEquals("must not be empty", ExceptionDetail.getDetail(e, "defaultCode").getErrorMessage());

			Assert.assertEquals("Engilsh message", ExceptionDetail.getDetail(e, "customCode").getErrorMessage());
			Assert.assertEquals("customCode", ExceptionDetail.getDetail(e, "customCode").getErrorCode());

			Assert.assertEquals("message", ExceptionDetail.getDetail(e, "customStringCode").getErrorMessage());
			Assert.assertEquals("customStringCode", ExceptionDetail.getDetail(e, "customStringCode").getErrorCode());
			Assert.assertEquals(ExceptionDetailMapper.TEMPLATE_FORMAT_INCORRECT, ExceptionDetail.getDetail(e, "customStringCode").getMetaData().get("i18n"));

			Assert.assertEquals("{spring.commons}", ExceptionDetail.getDetail(e, "noStringMessage").getErrorMessage());
			Assert.assertEquals("noStringMessage", ExceptionDetail.getDetail(e, "noStringMessage").getErrorCode());
			Assert.assertEquals(ExceptionDetailMapper.TEMPLATE_NOT_FOUND, ExceptionDetail.getDetail(e, "noStringMessage").getMetaData().get("i18n"));
		}
	}

	@Test
	public void withI18NMessage_Spanish() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "es-ES");
		HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(new CustomValidationFooObject("", ""), headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/validation/custom", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("defaultCode", ExceptionDetail.getDetail(e, "defaultCode").getErrorCode());
			Assert.assertEquals("no debe estar vacío", ExceptionDetail.getDetail(e, "defaultCode").getErrorMessage());

			Assert.assertEquals("customCode", ExceptionDetail.getDetail(e, "customCode").getErrorCode());
			Assert.assertEquals("Español message", ExceptionDetail.getDetail(e, "customCode").getErrorMessage());

			Assert.assertEquals("{spring.commons}", ExceptionDetail.getDetail(e, "noStringMessage").getErrorMessage());
			Assert.assertEquals("message", ExceptionDetail.getDetail(e, "customStringCode").getErrorMessage());
		}
	}

	@Test
	public void withI18NMessage_French() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "fr-FR");
		HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(new CustomValidationFooObject("", ""), headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/validation/custom", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("defaultCode", ExceptionDetail.getDetail(e, "defaultCode").getErrorCode());
			Assert.assertEquals("ne doit pas être vide", ExceptionDetail.getDetail(e, "defaultCode").getErrorMessage());

			Assert.assertEquals("customCode", ExceptionDetail.getDetail(e, "customCode").getErrorCode());
			Assert.assertEquals("French message", ExceptionDetail.getDetail(e, "customCode").getErrorMessage());

			Assert.assertEquals("{spring.commons}", ExceptionDetail.getDetail(e, "noStringMessage").getErrorMessage());
			Assert.assertEquals("message", ExceptionDetail.getDetail(e, "customStringCode").getErrorMessage());
		}
	}
}
