package com.github.damianwajser.tests.strings;

import com.github.damianwajser.model.strings.AlphaAllowSpaceStringObject;
import com.github.damianwajser.model.strings.AlphaMinOneStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlphaNumericValidationTest {

	@Test
	public void size_without_space_string() throws Exception {
		assertThat(validationFor(new AlphaMinOneStringObject(), onField("value")), fails());
		assertThat(validationFor(new AlphaMinOneStringObject(), onField("nulleable")), succedes());

		assertThat(validationFor(new AlphaMinOneStringObject(""), onField("nulleable")), fails());
		assertThat(validationFor(new AlphaMinOneStringObject(""), onField("value")), fails());
		//fail for size
		assertThat(validationFor(new AlphaMinOneStringObject("1234567891234"), onField("value")), fails());
		//fails for alpha numeric
		assertThat(validationFor(new AlphaMinOneStringObject("12_34"), onField("value")), fails());
		//fails for alpha numeric and string
		assertThat(validationFor(new AlphaMinOneStringObject("da 34"), onField("value")), fails());
		// ok min 1
		assertThat(validationFor(new AlphaMinOneStringObject("4"), onField("value")), succedes());
		// ok min 5
		assertThat(validationFor(new AlphaMinOneStringObject("12342"), onField("value")), succedes());
		assertThat(validationFor(new AlphaMinOneStringObject("12342"), onField("nulleable")), succedes());

	}

	@Test
	public void size_with_space_string() throws Exception {
		assertThat(validationFor(new AlphaAllowSpaceStringObject(), onField("value")), fails());
		assertThat(validationFor(new AlphaAllowSpaceStringObject(""), onField("value")), fails());
		//fail for size
		assertThat(validationFor(new AlphaAllowSpaceStringObject("1234567891234"), onField("value")), fails());
		//fails for alpha numeric
		assertThat(validationFor(new AlphaAllowSpaceStringObject("12_34"), onField("value")), fails());
		//fails for alpha numeric and string
		assertThat(validationFor(new AlphaAllowSpaceStringObject("da 34"), onField("value")), succedes());
		// ok min 1
		assertThat(validationFor(new AlphaAllowSpaceStringObject("4"), onField("value")), succedes());
		// ok min 5
		assertThat(validationFor(new AlphaAllowSpaceStringObject("12342"), onField("value")), succedes());
	}

}
