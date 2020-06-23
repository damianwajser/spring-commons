package com.github.damianwajser.tests.enums.contry;

import com.github.damianwajser.model.enums.global.MatchEnumObject;
import com.github.damianwajser.model.enums.global.MatchString;
import com.github.damianwajser.validator.constraint.enums.values.Countries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class MatchEnumValidationTest {

	@Test
	public void CountryEnum() throws Exception {
		assertThat(validationFor(new MatchEnumObject(), onField("value")), fails());
		assertThat(validationFor(new MatchEnumObject(Countries.ARG), onField("value")), succedes());
	}

	@Test
	public void CountryString() throws Exception {
		assertThat(validationFor(new MatchString(), onField("value")), fails());
		assertThat(validationFor(new MatchString("ASDFGH"), onField("value")), fails());
		assertThat(validationFor(new MatchString("ARG"), onField("value")), succedes());

	}

}
