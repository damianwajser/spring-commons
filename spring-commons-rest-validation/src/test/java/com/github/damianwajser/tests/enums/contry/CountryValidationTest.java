package com.github.damianwajser.tests.enums.contry;

import com.github.damianwajser.model.enums.contry.CountryEnum;
import com.github.damianwajser.model.enums.contry.CountryString;
import com.github.damianwajser.validator.constraint.enums.values.Countries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
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
