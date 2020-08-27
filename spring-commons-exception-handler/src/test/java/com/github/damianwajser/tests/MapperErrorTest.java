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

@SpringBootTest
public class MapperErrorTest {
	@Test
	public void ParseMessage() throws Exception {
		String bodyCammel = "{\"details\":[{\"errorCode\":\"400\",\"errorMessage\":\"badrequest\",\"errorDetail\":null,\"metaData\":null}]}";
		Assert.assertEquals("400", TestUtils.getMessage(bodyCammel).getDetails().get(0).getErrorCode());
		String bodySnake = "{\"details\":[{\"error_code\":\"400\",\"error_message\":\"badrequest\",\"error_detail\":null,\"meta_data\":null}]}";
		Assert.assertEquals("400", TestUtils.getMessage(bodySnake).getDetails().get(0).getErrorCode());
	}
}
