package com.github.damianwajser.tests.number;

import com.github.damianwajser.model.number.DigitsDecimalObject;
import com.github.damianwajser.model.number.DigitsWithoutDecimalAndMultiplyObject;
import com.github.damianwajser.model.number.DigitsWithoutDecimalAndMultiplyObjectInt;
import com.github.damianwajser.model.number.DigitsWithoutDecimalObject;
import org.junit.Test;

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

		assertThat(validationFor(new DigitsWithoutDecimalObject(new BigDecimal("1"))), succedes());
		assertThat(validationFor(new DigitsWithoutDecimalObject(new BigDecimal("1.0"))), fails());

		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObjectInt(10)), succedes());
		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObjectInt(100)), succedes());
		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObjectInt(1000)), succedes());
		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObjectInt(11)), fails());

		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObject(new BigDecimal("10"))), succedes());
		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObject(new BigDecimal("100"))), succedes());
		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObject(new BigDecimal("1000"))), succedes());
		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObject(new BigDecimal("11"))), fails());
		assertThat(validationFor(new DigitsWithoutDecimalAndMultiplyObject(new BigDecimal("11.10"))), fails());

	}
}
