package com.github.damianwajser.tests;

import com.github.damianwajser.emv.exceptions.CrcValidationException;
import com.github.damianwajser.emv.exceptions.EmvFormatException;
import com.github.damianwajser.emv.parser.EmvCoParser;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class EmvCoParserTest {

	@Test
	public void ok() throws Exception {
		String qr = "00020101021102080365930704084166294126180002CA01083344220144110007com.adq50250004CUIT011320-11111111-2520499885303032540510.005802AR5909TODO PAGO6004CABA62350019www.todopago.com.ar07081231231280160006COMPRA01020081370006INFOTX01042343020110310092212581182270005CABAL01053252302055433583300010MASTERCARD010334202055433563049275";
		Map<Integer, Object> emv = EmvCoParser.parse(qr);
		System.out.println(emv);
		assertThat(emv).containsEntry(0, "01");
		assertThat(emv).containsEntry(1, "11");
		assertThat(emv).containsEntry(2, "03659307");
		assertThat(emv).containsEntry(4, "41662941");
		System.out.println(emv.get(26).toString());

		assertThat((Map<Integer, Object>) emv.get(26)).containsEntry(0, "CA");

	}

	@Test
	public void OkWhith00_00() throws Exception {
		String qr = "00020101021226180002CA01080406001804083488730702080022977344110007com.adq50250004CUIT011300-00000000-052049999530303254045.615802AR5917PRUEBAS FRANCO QR6004CABA62420026www.prismamediosdepago.com07081200075280160006Compra01020081390006INFOTX0102810201103100105124153040082210004VISA010279020387883210004PRIS01027702032286304cf93";
		Map<Integer, Object> emv = EmvCoParser.parse(qr, true);
		System.out.println(emv);
		assertThat(emv).containsEntry(0, "01");
		assertThat(emv).containsEntry(1, "12");
		assertThat(emv).containsEntry(2, "00229773");
		assertThat(emv).containsEntry(4, "34887307");
		assertThat((Map<Integer, Object>) emv.get(26)).containsEntry(0, "CA");

	}

	@Test(expected = CrcValidationException.class)
	public void invalidCRC() throws Exception {
		String qr = "0002010102110200365930704084166294126180002CA01083344220144110007com.adc50250004CUIT011320-11111111-2520499885303032540510.005802AR5909TODO PAGO6004CABA62350019www.todopago.com.ar07081231231280160006COMPRA01020081370006INFOTX01042343020110310092212581182270005CABAL01053252302055433583300010MASTERCARD010334202055433563049275";
		Map<Integer, Object> emv = EmvCoParser.parse(qr);
	}

	@Test(expected = EmvFormatException.class)
	public void malformedQr() throws Exception {
		String qr = "0002010102110200365930704084166294126180002CA01083344220144110007com.adc50250004CUIT011320-11111111-2520499885303032540510.005802AR5909TODO PAGO6004CABA62350019www.todopago.com.ar07081231231280160006COMPRA01020081370006INFOTX01042343020110310092212581182270005CABAL01053252302055433583300010MASTERCARD010334202055433563049275";
		Map<Integer, Object> emv = EmvCoParser.parse(qr, true);
	}

	@Test()
	public void invalidCRCAndParse() throws Exception {
		String qr = "00020101021102080366930704084166294126180002CA01083344220144110007com.ahq50250004CUIT011320-11111111-2520499885303032540510.005802AR5909TODO PAGO6004CABA62350019www.todopago.com.ar07081231231280160006COMPRA01020081370006INFOTX01042343020110310092212581182270005CABAL01053252302055433583300010MASTERCARD010334202055433563049275";
		try {
			Map<Integer, Object> emv = EmvCoParser.parse(qr, true);
			failBecauseExceptionWasNotThrown(CrcValidationException.class);
		} catch (CrcValidationException e) {
			Map<Integer, Object> emv = EmvCoParser.parse(qr, false);
			assertThat(emv).containsEntry(0, "01");
			assertThat(emv).containsEntry(1, "11");
			assertThat(emv).containsEntry(2, "03669307");
			assertThat(emv).containsEntry(4, "41662941");
			assertThat((Map<Integer, Object>) emv.get(26)).containsEntry(0, "CA");

		}
	}
}
