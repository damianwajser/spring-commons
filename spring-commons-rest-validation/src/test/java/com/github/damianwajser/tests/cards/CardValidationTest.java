package com.github.damianwajser.tests.cards;

import com.github.damianwajser.model.cards.ExpirationObject;
import org.junit.Test;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CardValidationTest {

	@Test
	public void expirationMonth() throws Exception {
		assertThat(validationFor(new ExpirationObject()), fails());
		assertThat(validationFor(new ExpirationObject(10, 30)), succedes());
		assertThat(validationFor(new ExpirationObject(11, 26)), succedes());
		assertThat(validationFor(new ExpirationObject(5, 25)), succedes());
		assertThat(validationFor(new ExpirationObject(2, 19)), fails());
		assertThat(validationFor(new ExpirationObject(99, 25)), fails());
	}

}
