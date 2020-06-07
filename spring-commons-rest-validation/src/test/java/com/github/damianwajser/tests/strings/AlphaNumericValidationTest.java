package com.github.damianwajser.tests.strings;

import com.github.damianwajser.model.strings.AlphaAllowSpaceStringObject;
import com.github.damianwajser.model.strings.AlphaMinOneStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class AlphaNumericValidationTest {

	@Test
	public void size_without_space_string() throws Exception {
		assertThat(validationFor(new AlphaMinOneStringObject(), onField("value")), fails());
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
