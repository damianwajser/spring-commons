package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.model.ErrorMessage;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ErrorMessageTest {

	@Test
	public void ErrorMessageTest() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ExceptionDetail detail = new ExceptionDetail("", "", Optional.empty());
		ErrorMessage errorMessage = new ErrorMessage(Arrays.asList(detail), request);
		ErrorMessage errorMessage1 = new ErrorMessage(Arrays.asList(detail), request);
		System.out.println(errorMessage);
		Assert.assertEquals(errorMessage, errorMessage1);

	}

}
