package com.github.damianwajser.tests.strings;

import com.github.damianwajser.model.strings.UUIDObject;
import org.junit.Test;

import java.util.UUID;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UUIDTest {

	@Test
	public void password_string() throws Exception {
		assertThat(validationFor(new UUIDObject(), onField("value")), fails());
		assertThat(validationFor(new UUIDObject(""), onField("value")), fails());
		assertThat(validationFor(new UUIDObject("n!k@s"), onField("value")), fails());
		assertThat(validationFor(new UUIDObject("gregorymarjames-law"), onField("value")), fails());
		assertThat(validationFor(new UUIDObject("1231231-sdgxfgh-234534t-dsgasdg"), onField("value")), fails());
		assertThat(validationFor(new UUIDObject("1231231-sdgxfgh-234534t-dsgasdg-asfwteseg4"), onField("value")), fails());

		assertThat(validationFor(new UUIDObject(UUID.randomUUID().toString()), onField("value")), succedes());
	}
}
