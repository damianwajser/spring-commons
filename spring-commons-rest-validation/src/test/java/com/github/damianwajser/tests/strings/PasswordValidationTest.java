package com.github.damianwajser.tests.strings;

import com.github.damianwajser.model.strings.PasswordStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PasswordValidationTest {

	@Test
	public void password_string() throws Exception {
		assertThat(validationFor(new PasswordStringObject(), onField("value")), fails());
		assertThat(validationFor(new PasswordStringObject(""), onField("value")), fails());
		assertThat(validationFor(new PasswordStringObject("n!k@s"), onField("value")), fails());
		assertThat(validationFor(new PasswordStringObject("gregorymarjames-law"), onField("value")), fails());
		assertThat(validationFor(new PasswordStringObject("abcdFg45*"), onField("value")), fails());
		assertThat(validationFor(new PasswordStringObject("n!koabcD#AX"), onField("value")), fails());
		assertThat(validationFor(new PasswordStringObject("ABCASWF2!"), onField("value")), fails());

		assertThat(validationFor(new PasswordStringObject("n!k@sn1Kos"), onField("value")), succedes());
		assertThat(validationFor(new PasswordStringObject("J@vaC0deG##ks"), onField("value")), succedes());
		assertThat(validationFor(new PasswordStringObject("n!k1abcD#!"), onField("value")), succedes());
	}
}
