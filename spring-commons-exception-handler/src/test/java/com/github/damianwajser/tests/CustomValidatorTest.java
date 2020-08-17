package com.github.damianwajser.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.model.FooObject;
import com.github.damianwajser.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomValidatorTest {
	@LocalServerPort
	private int port;

	private RestTemplate restTemplate = new RestTemplate();


	@Test
	public void failWhenServiceReturnsConstraintViolationException() throws JsonProcessingException {
		try{
			FooObject object = new FooObject();
			HttpEntity httpEntity = new HttpEntity(object);
			this.restTemplate.exchange("http://localhost:" + port + "/custom_validator/custom", HttpMethod.POST, httpEntity, FooObject.class);
		}catch (HttpClientErrorException exception){
			assertThat( exception.getStatusCode(), is(HttpStatus.BAD_REQUEST) );

			ErrorMessage exceptionDetail = TestUtils.getMessage( exception );
			assertThat( exceptionDetail.getDetails().get(0).getErrorCode(), is("400") );
		}
	}

}
