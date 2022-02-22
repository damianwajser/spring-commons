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

import static com.github.damianwajser.exceptions.handlers.ExceptionDetailMapper.I18N_KEY;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionI18nTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();


	@Test
	public void withDefaultMessage() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "en-EN");
		HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(null, headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/i18n/literal", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("badrequest", ExceptionDetail.getDetail(e, "literal").getErrorMessage());
			Assert.assertEquals("literal", ExceptionDetail.getDetail(e, "literal").getErrorCode());
			Assert.assertEquals(ExceptionDetailMapper.TEMPLATE_FORMAT_INCORRECT, ExceptionDetail.getDetail(e, "literal").getMetaData().get(I18N_KEY));
		}
	}

	@Test
	public void withNotFoundMessage() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", "en-EN");
		HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(null, headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/i18n/withproperties/notfound", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals("{spring.commons}", ExceptionDetail.getDetail(e, "notfound").getErrorMessage());
			Assert.assertEquals("notfound", ExceptionDetail.getDetail(e, "notfound").getErrorCode());
			Assert.assertEquals(ExceptionDetailMapper.TEMPLATE_NOT_FOUND, ExceptionDetail.getDetail(e, "notfound").getMetaData().get(I18N_KEY));
		}
	}

	@Test
	public void withPropertiesSpanish() throws Exception {
		withPropertiesLenguaje("es-ES", "Español message");
	}

	@Test
	public void withPropertiesEnglish() throws Exception {
		withPropertiesLenguaje("en-EN", "Engilsh message");
	}

	@Test
	public void withPropertiesFrench() throws Exception {
		withPropertiesLenguaje("fr-FR", "French message");
	}

	public void withPropertiesLenguaje(String language, String assertMessage) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Accept-Language", language);
		HttpEntity<CustomValidationFooObject> entity = new HttpEntity<>(null, headers);
		try {
			this.restTemplate.exchange("http://localhost:" + port + "/i18n/withproperties", HttpMethod.POST, entity,
					Object.class);
			Assert.fail();
		} catch (HttpClientErrorException.BadRequest e) {
			Assert.assertEquals(assertMessage, ExceptionDetail.getDetail(e, "withproperties").getErrorMessage());
			Assert.assertEquals("withproperties", ExceptionDetail.getDetail(e, "withproperties").getErrorCode());
			Assert.assertNull(ExceptionDetail.getDetail(e, "withproperties").getMetaData().get(I18N_KEY));
		}
	}
}
