package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.min.MinNumberObject;
import com.github.damianwajser.model.global.min.MinPrimitiveObject;
import com.github.damianwajser.model.global.min.MinStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class MinValidationTest {

	@Test
	public void min_string() throws Exception {
		assertThat(validationFor(new MinStringObject(), onField("value")), fails());
		assertThat(validationFor(new MinStringObject("1"), onField("value")), fails());
		assertThat(validationFor(new MinStringObject("423456"), onField("value")), fails());
		assertThat(validationFor(new MinStringObject(""), onField("value")), fails());
	}

	@Test
	public void min_number() throws Exception {
		assertThat(validationFor(new MinNumberObject()), fails());
		assertThat(validationFor(new MinNumberObject(3l)), fails());
		assertThat(validationFor(new MinNumberObject(2l)), fails());
		assertThat(validationFor(new MinNumberObject(4l)), succedes());

	}

	@Test
	public void min_number_obect() throws Exception {
		assertThat(validationFor(new MinPrimitiveObject()), fails());
		assertThat(validationFor(new MinPrimitiveObject(3)), succedes());
		assertThat(validationFor(new MinPrimitiveObject(2)), fails());
		assertThat(validationFor(new MinPrimitiveObject(1)), fails());

	}

}
