package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.nonull.NoNullCollectionObject;
import com.github.damianwajser.model.global.nonull.NoNullStringObject;
import org.junit.Test;

import java.util.Arrays;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotNullTest {

	@Test
	public void not_null_string() throws Exception {
		assertThat(validationFor(new NoNullStringObject(), onField("value")), fails());
		assertThat(validationFor(new NoNullStringObject("4"), onField("value")), succedes());
	}

	@Test
	public void noNull_collection() throws Exception {
		assertThat(validationFor(new NoNullCollectionObject()), fails());
		assertThat(validationFor(new NoNullCollectionObject(Arrays.asList(null, null))), succedes());
		assertThat(validationFor(new NoNullCollectionObject(Arrays.asList(1, 2, 3, 4, 5, 6))), succedes());
	}

}
