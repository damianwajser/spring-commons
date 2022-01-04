package com.github.damianwajser.tests.strings;

import com.github.damianwajser.model.strings.WordAllowSpaceStringObject;
import com.github.damianwajser.model.strings.WordMinOneStringObject;
import org.junit.Test;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class WordValidationTest {

	@Test
	public void size_without_space_string() throws Exception {
		assertThat(validationFor(new WordMinOneStringObject(), onField("value")), fails());
		assertThat(validationFor(new WordMinOneStringObject(), onField("nulleable")), succedes());

		assertThat(validationFor(new WordMinOneStringObject(""), onField("nulleable")), fails());
		assertThat(validationFor(new WordMinOneStringObject(""), onField("value")), fails());
		//fail for size
		assertThat(validationFor(new WordMinOneStringObject("qwertyqwertyq"), onField("value")), fails());
		//fails for alpha numeric
		assertThat(validationFor(new WordMinOneStringObject("ab_cd"), onField("value")), fails());
		//fails for alpha numeric and string
		assertThat(validationFor(new WordMinOneStringObject("da da"), onField("value")), fails());
		// ok min 1
		assertThat(validationFor(new WordMinOneStringObject("h"), onField("value")), succedes());
		// ok min 5
		assertThat(validationFor(new WordMinOneStringObject("qwert"), onField("value")), succedes());
		assertThat(validationFor(new WordMinOneStringObject("qwert"), onField("nulleable")), succedes());
		// fails for number
		assertThat(validationFor(new WordMinOneStringObject("qwerty1"), onField("nulleable")), fails());
	}

	@Test
	public void size_with_space_string() throws Exception {
		assertThat(validationFor(new WordAllowSpaceStringObject(), onField("value")), fails());
		assertThat(validationFor(new WordAllowSpaceStringObject(""), onField("value")), fails());
		//fail for size
		assertThat(validationFor(new WordAllowSpaceStringObject("qwertyqwertyq"), onField("value")), fails());
		//fails for alpha numeric
		assertThat(validationFor(new WordAllowSpaceStringObject("ab_cd"), onField("value")), fails());
		//fails for alpha numeric and string
		assertThat(validationFor(new WordAllowSpaceStringObject("da da"), onField("value")), succedes());
		// ok min 1
		assertThat(validationFor(new WordAllowSpaceStringObject("h"), onField("value")), succedes());
		// ok min 5
		assertThat(validationFor(new WordAllowSpaceStringObject("qwert"), onField("value")), succedes());
		// fails for number
		assertThat(validationFor(new WordMinOneStringObject("qwer ty1"), onField("nulleable")), fails());
	}

}
