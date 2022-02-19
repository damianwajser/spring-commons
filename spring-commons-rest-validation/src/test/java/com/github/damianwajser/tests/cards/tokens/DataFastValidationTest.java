package com.github.damianwajser.tests.cards.tokens;

import com.github.damianwajser.model.cards.tokens.CardDataFastTokenObject;
import com.github.damianwajser.model.cards.tokens.CardTokenExObject;
import org.junit.Test;

import java.util.UUID;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DataFastValidationTest {

	@Test
	public void cardToken() throws Exception {
		assertThat(validationFor(new CardDataFastTokenObject(), onField("value")), fails());
		assertThat(validationFor(new CardDataFastTokenObject("1234567891234"), onField("value")), fails());
		assertThat(validationFor(new CardDataFastTokenObject("123456asd72340"), onField("value")), fails());
		assertThat(validationFor(new CardDataFastTokenObject("3456asd72340"), onField("value")), fails());
		assertThat(validationFor(new CardDataFastTokenObject("asdasdasdasdasdasdasdasdasdasdasdasdasd"), onField("value")), fails());
		assertThat(validationFor(new CardDataFastTokenObject("8ac7a4a07ef7c8ba017f0387416e2521"), onField("value")), succedes());
		assertThat(validationFor(new CardDataFastTokenObject(UUID.randomUUID().toString().replaceAll("-","")), onField("value")), succedes());
	}

}
