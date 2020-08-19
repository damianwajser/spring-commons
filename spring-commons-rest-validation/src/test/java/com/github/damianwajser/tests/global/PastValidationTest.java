package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.PastAuxObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class PastValidationTest {

	@Test
	public void past_obect() throws Exception {
		assertThat(validationFor(new PastAuxObject(), onField("value")), fails());
		assertThat(validationFor(new PastAuxObject(), onField("nulleable")), succedes());
		assertThat(validationFor(new PastAuxObject(1), onField("value")), fails());

		assertThat(validationFor(new PastAuxObject(LocalDate.now()), onField("value")), succedes());
		assertThat(validationFor(new PastAuxObject(LocalDate.now().minusDays(1)), onField("value")), succedes());
		assertThat(validationFor(new PastAuxObject(LocalDate.now().plusDays(1)), onField("value")), fails());

		assertThat(validationFor(new PastAuxObject(new Date()), onField("value")), succedes());

		assertThat(validationFor(new PastAuxObject(LocalDateTime.now().plusSeconds(10)), onField("value")), fails());
		assertThat(validationFor(new PastAuxObject(LocalDateTime.now().minusSeconds(10)), onField("value")), succedes());

	}
}
