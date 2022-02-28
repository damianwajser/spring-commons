package com.github.damianwajser.tests.enums.contry;

import com.github.damianwajser.model.enums.global.MatchEnumObjectArray;
import com.github.damianwajser.validator.constraint.enums.values.Countries;
import org.junit.Test;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MatchEnumArrayValidationTest {

	@Test
	public void CountryEnum() throws Exception {
		assertThat(validationFor(new MatchEnumObjectArray(), onField("value")), fails());
		assertThat(validationFor(new MatchEnumObjectArray(new Countries[]{Countries.ARG}), onField("value")), succedes());
	}


}
