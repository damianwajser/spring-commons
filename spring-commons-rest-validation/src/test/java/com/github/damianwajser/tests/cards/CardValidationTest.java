package com.github.damianwajser.tests.cards;

import com.github.damianwajser.model.cards.CardTokenObject;
import com.github.damianwajser.model.cards.ExpirationObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class CardValidationTest {

	@Test
	public void cardToken() throws Exception {
		assertThat(validationFor(new CardTokenObject(), onField("value")), fails());
		assertThat(validationFor(new CardTokenObject("1234567891234"), onField("value")), succedes());
		assertThat(validationFor(new CardTokenObject("123456asd72340"), onField("value")), succedes());
		assertThat(validationFor(new CardTokenObject("3456asd72340"), onField("value")), fails());
		assertThat(validationFor(new CardTokenObject("asdasdasdasdasdasdasdasd"), onField("value")), fails());
	}

	@Test
	public void expirationMonth() throws Exception {
		assertThat(validationFor(new ExpirationObject()), fails());
		assertThat(validationFor(new ExpirationObject(10, 30)), succedes());
		assertThat(validationFor(new ExpirationObject(2, 19)), fails());

	}

}
