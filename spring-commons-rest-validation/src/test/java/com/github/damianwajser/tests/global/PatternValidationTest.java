package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.pattern.PatternStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatternValidationTest {

	@Test
	public void pattern() throws Exception {
		assertThat(validationFor(new PatternStringObject(), onField("value")), fails());
		assertThat(validationFor(new PatternStringObject("aalice@example.company.in"), onField("value")), succedes());
		assertThat(validationFor(new PatternStringObject("alice_bob@example.com"), onField("value")), succedes());
		assertThat(validationFor(new PatternStringObject("alice@example.co.in"), onField("value")), succedes());
		assertThat(validationFor(new PatternStringObject("alice@example.com"), onField("value")), succedes());
		assertThat(validationFor(new PatternStringObject("alice.bob@example.com"), onField("value")), succedes());
		assertThat(validationFor(new PatternStringObject("alice@example.me.org"), onField("value")), succedes());

		assertThat(validationFor(new PatternStringObject("alice.example.com"), onField("value")), fails());
		assertThat(validationFor(new PatternStringObject("alice..bob @ example.com"), onField("value")), fails());
		assertThat(validationFor(new PatternStringObject("alice @ .example.me.org"), onField("value")), fails());

		assertThat(validationFor(new PatternStringObject(".alice @ ejemplo.com"), onField("value")), fails());
		assertThat(validationFor(new PatternStringObject("alice@ejemplo.com."), onField("value")), fails());
		assertThat(validationFor(new PatternStringObject("alice@example.c"), onField("value")), fails());
		assertThat(validationFor(new PatternStringObject("alice@example.companyyy"), onField("value")), fails());
	}

}
