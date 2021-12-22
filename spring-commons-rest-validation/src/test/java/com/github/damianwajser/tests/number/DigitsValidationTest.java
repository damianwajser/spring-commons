package com.github.damianwajser.tests.number;

import com.github.damianwajser.model.number.DigitsDecimalObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static com.github.damianwajser.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DigitsValidationTest {

	@Test
	public void digits_decimal() throws Exception {
		assertThat(validationFor(new DigitsDecimalObject()), fails());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("1.0"))), succedes());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("22.00"))), succedes());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("1.2"))), succedes());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("11.99"))), succedes());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("111.99"))), succedes());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("11.999"))), fails());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("111.999"))), fails());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("1111.999"))), fails());
		assertThat(validationFor(new DigitsDecimalObject(new BigDecimal("11.999"))), fails());

	}
}
