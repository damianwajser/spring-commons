package com.github.damianwajser.tests.enums.contry;

import com.github.damianwajser.model.enums.contry.CountryEnum;
import com.github.damianwajser.model.enums.contry.CountryString;
import com.github.damianwajser.validator.constraint.enums.values.Countries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CountryValidationTest {

	@Test
	public void CountryEnum() throws Exception {
		assertThat(validationFor(new CountryEnum(), onField("value")), fails());
		assertThat(validationFor(new CountryEnum(Countries.ARG), onField("value")), succedes());
	}

	@Test
	public void CountryString() throws Exception {
		assertThat(validationFor(new CountryString(), onField("value")), fails());
		assertThat(validationFor(new CountryString("ASDFGH"), onField("value")), fails());
		assertThat(validationFor(new CountryString("ARG"), onField("value")), succedes());

	}

}
