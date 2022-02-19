package com.github.damianwajser.tests.cards.tokens;

import com.github.damianwajser.model.cards.tokens.CardTokenExObject;
import org.junit.Test;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TokenExValidationTest {

	@Test
	public void cardToken() throws Exception {
		assertThat(validationFor(new CardTokenExObject(), onField("value")), fails());
		assertThat(validationFor(new CardTokenExObject("1234567891234"), onField("value")), succedes());
		assertThat(validationFor(new CardTokenExObject("123456asd72340"), onField("value")), succedes());
		assertThat(validationFor(new CardTokenExObject("3456asd72340"), onField("value")), fails());
		assertThat(validationFor(new CardTokenExObject("asdasdasdasdasdasdasdasd"), onField("value")), fails());
	}

}
