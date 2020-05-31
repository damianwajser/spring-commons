package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.size.SizeCollectionObject;
import com.github.damianwajser.model.global.size.SizeMinOneStringObject;
import com.github.damianwajser.model.global.size.SizeMinZeroStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class SizeValidationTest {

	@Test
	public void size_string() throws Exception {
		assertThat(validationFor(new SizeMinZeroStringObject(), onField("value")), succedes());
		assertThat(validationFor(new SizeMinOneStringObject(), onField("value")), fails());
		assertThat(validationFor(new SizeMinZeroStringObject("1234567891234"), onField("value")), fails());
		assertThat(validationFor(new SizeMinZeroStringObject("4"), onField("value")), succedes());
		assertThat(validationFor(new SizeMinZeroStringObject("12342"), onField("value")), succedes());
		assertThat(validationFor(new SizeMinZeroStringObject(""), onField("value")), succedes());
		assertThat(validationFor(new SizeMinOneStringObject(""), onField("value")), succedes());
	}

	@Test
	public void size_collection() throws Exception {
		assertThat(validationFor(new SizeCollectionObject()), succedes());
		assertThat(validationFor(new SizeCollectionObject(Arrays.asList(1, 2, 4, 5))), succedes());
		assertThat(validationFor(new SizeCollectionObject(Arrays.asList(1, 2, 3, 4, 5, 6))), fails());

	}

}
