package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.onenotnull.OneNotNullObject;
import org.junit.Test;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OneNotNullTest {

	@Test
	public void oneNotNull() {
		assertThat(validationFor(new OneNotNullObject()), fails());
		assertThat(validationFor(new OneNotNullObject(null,null)), fails());
		assertThat(validationFor(new OneNotNullObject(null,"value2")), succedes());
		assertThat(validationFor(new OneNotNullObject("value2",null)), succedes());
	}

}
