package com.github.damianwajser.tests;

import com.github.damianwajser.model.CardTokenObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.Validation;
import javax.validation.Validator;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CardValidation {

	@Test
	public void cardToken() throws Exception {
		assertThat(validationFor(new CardTokenObject(), onField("value")), fails());
		assertThat(validationFor(new CardTokenObject("1234567891234"), onField("value")), succedes());
		assertThat(validationFor(new CardTokenObject("123456asd72340"), onField("value")), succedes());
		assertThat(validationFor(new CardTokenObject("3456asd72340"), onField("value")), fails());
		assertThat(validationFor(new CardTokenObject("asdasdasdasdasdasdasdasd"), onField("value")), fails());
	}

}
