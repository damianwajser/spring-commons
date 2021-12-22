package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.size.SizeCollectionObject;
import com.github.damianwajser.model.global.size.SizeMinOneStringObject;
import com.github.damianwajser.model.global.size.SizeMinZeroStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SizeValidationTest {

	@Test
	public void size_string() throws Exception {
		assertThat(validationFor(new SizeMinZeroStringObject(), onField("value")), fails());
		assertThat(validationFor(new SizeMinZeroStringObject(), onField("nulleable")), succedes());

		assertThat(validationFor(new SizeMinZeroStringObject(""), onField("value")), succedes());
		assertThat(validationFor(new SizeMinZeroStringObject("1234567891234"), onField("value")), fails());
		assertThat(validationFor(new SizeMinZeroStringObject("4"), onField("value")), succedes());
		assertThat(validationFor(new SizeMinZeroStringObject("12342"), onField("value")), succedes());
		assertThat(validationFor(new SizeMinZeroStringObject(""), onField("value")), succedes());

		assertThat(validationFor(new SizeMinOneStringObject(""), onField("value")), fails());
		assertThat(validationFor(new SizeMinOneStringObject(), onField("value")), fails());
	}

	@Test
	public void size_collection() throws Exception {
		assertThat(validationFor(new SizeCollectionObject()), fails());//no es nnulleable
		assertThat(validationFor(new SizeCollectionObject(new ArrayList<>())), succedes());//no es nnulleable
		assertThat(validationFor(new SizeCollectionObject(Arrays.asList(1, 2, 4, 5))), succedes());
		assertThat(validationFor(new SizeCollectionObject(Arrays.asList(1, 2, 3, 4, 5, 6))), fails());
	}

}
