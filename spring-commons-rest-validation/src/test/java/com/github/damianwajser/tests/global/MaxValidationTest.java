package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.max.MaxNumberObject;
import com.github.damianwajser.model.global.max.MaxPrimitiveObject;
import com.github.damianwajser.model.global.max.MaxStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaxValidationTest {

	@Test
	public void max_string() throws Exception {
		assertThat(validationFor(new MaxStringObject(), onField("value")), fails());
		assertThat(validationFor(new MaxStringObject(), onField("nulleable")), succedes());
		assertThat(validationFor(new MaxStringObject("1"), onField("value")), fails());
		assertThat(validationFor(new MaxStringObject("423456"), onField("value")), fails());
		assertThat(validationFor(new MaxStringObject(""), onField("value")), fails());
		assertThat(validationFor(new MaxStringObject(), onField("aliases")), fails());
		assertThat(validationFor(new MaxStringObject(), onField("invalidAliases")), fails());
	}

	@Test
	public void max_number() throws Exception {
		assertThat(validationFor(new MaxNumberObject()), fails());
		assertThat(validationFor(new MaxNumberObject(4l), onField("value")), fails());
		assertThat(validationFor(new MaxNumberObject(3l), onField("value")), succedes());
		assertThat(validationFor(new MaxNumberObject(2l), onField("value")), succedes());
		assertThat(validationFor(new MaxNumberObject(null, 1l), onField("aliases")), succedes());

	}

	@Test
	public void max_number_obect() throws Exception {
		assertThat(validationFor(new MaxPrimitiveObject()), succedes());
		assertThat(validationFor(new MaxPrimitiveObject(1)), succedes());
		assertThat(validationFor(new MaxPrimitiveObject(2)), succedes());
		assertThat(validationFor(new MaxPrimitiveObject(3)), fails());
	}

}
