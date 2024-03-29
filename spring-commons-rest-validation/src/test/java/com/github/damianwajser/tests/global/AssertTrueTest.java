package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.asserttrue.AssertTrueObject;
import com.github.damianwajser.model.global.asserttrue.AssertTrueStringObject;
import org.junit.Test;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AssertTrueTest {

	@Test
	public void true_Boolean() throws Exception {
		assertThat(validationFor(new AssertTrueObject(), onField("value")), fails());
		assertThat(validationFor(new AssertTrueObject(false), onField("value")), fails());
		assertThat(validationFor(new AssertTrueObject(true), onField("value")), succedes());
	}

	@Test
	public void true_object() throws Exception {
		assertThat(validationFor(new AssertTrueStringObject(), onField("value")), fails());
		assertThat(validationFor(new AssertTrueStringObject("false"), onField("value")), fails());
		assertThat(validationFor(new AssertTrueStringObject("true"), onField("value")), fails());
	}

}
