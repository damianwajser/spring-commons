package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.email.EmailObject;
import com.github.damianwajser.model.global.email.EmailStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailValidationTest {

	@Test
	public void email_string() throws Exception {
		assertThat(validationFor(new EmailStringObject(), onField("value")), fails());
		assertThat(validationFor(new EmailStringObject("aalice@example.company.in"), onField("value")), succedes());
		assertThat(validationFor(new EmailStringObject("alice_bob@example.com"), onField("value")), succedes());
		assertThat(validationFor(new EmailStringObject("alice@example.co.in"), onField("value")), succedes());
		assertThat(validationFor(new EmailStringObject("alice@example.com"), onField("value")), succedes());
		assertThat(validationFor(new EmailStringObject("alice.bob@example.com"), onField("value")), succedes());
		assertThat(validationFor(new EmailStringObject("alice@example.me.org"), onField("value")), succedes());

		assertThat(validationFor(new EmailStringObject("alice.example.com"), onField("value")), fails());
		assertThat(validationFor(new EmailStringObject("alice..bob @ example.com"), onField("value")), fails());
		assertThat(validationFor(new EmailStringObject("alice @ .example.me.org"), onField("value")), fails());

		assertThat(validationFor(new EmailStringObject(".alice @ ejemplo.com"), onField("value")), fails());
		assertThat(validationFor(new EmailStringObject("alice@ejemplo.com."), onField("value")), fails());
		assertThat(validationFor(new EmailStringObject("alice@example.c"), onField("value")), fails());
		assertThat(validationFor(new EmailStringObject("alice@example.companyyy"), onField("value")), fails());
	}

	@Test
	public void email_obect() throws Exception {
		assertThat(validationFor(new EmailObject(), onField("value")), fails());
		assertThat(validationFor(new EmailObject(1), onField("value")), fails());
		assertThat(validationFor(new EmailObject(new ArrayList<>()), onField("value")), fails());
	}

}
