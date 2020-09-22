package com.github.damianwajser.tests;

import com.github.damianwajser.emv.exceptions.CrcValidationException;
import com.github.damianwajser.emv.parser.EmvCoParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmvCoParserTest {

	@Test
	public void ok() throws Exception {
		String qr = "00020101021102080365930704084166294126180002CA01083344220144110007com.adq50250004CUIT011320-11111111-2520499885303032540510.005802AR5909TODO PAGO6004CABA62350019www.todopago.com.ar07081231231280160006COMPRA01020081370006INFOTX01042343020110310092212581182270005CABAL01053252302055433583300010MASTERCARD010334202055433563049275";
		Map<Integer, Object> emv = EmvCoParser.parse(qr);
		System.out.println(emv);
		assertThat(emv.get(0)).isEqualTo("01");
		assertThat(emv.get(1)).isEqualTo("11");
		assertThat(emv.get(2)).isEqualTo("03659307");

		assertThat(emv.get(4)).isEqualTo("41662941");
		System.out.println(emv.get(26).toString());
		assertThat(((Map<Integer, Object>) emv.get(26)).get(0)).isEqualTo("CA");

	}

	@Test(expected = CrcValidationException.class)
	public void invalidCRC() throws Exception {
		String qr = "0002010102110200365930704084166294126180002CA01083344220144110007com.adc50250004CUIT011320-11111111-2520499885303032540510.005802AR5909TODO PAGO6004CABA62350019www.todopago.com.ar07081231231280160006COMPRA01020081370006INFOTX01042343020110310092212581182270005CABAL01053252302055433583300010MASTERCARD010334202055433563049275";
		Map<Integer, Object> emv = EmvCoParser.parse(qr);
	}
}
