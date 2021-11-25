package com.github.damianwajser.tests;

import com.github.damianwajser.printers.QrWritter;
import com.github.damianwajser.printers.formatters.QrFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class PrinterTest {

	@Test
	public void ok() throws Exception {
		String text = "some text";
		QrWritter printer = new QrWritter();
		byte[] qrByte = printer.write(text);
		String decodeText = printer.readQR(qrByte, QrFormat.PNG);
		assertThat(decodeText).isEqualTo(text);
	}

	@Test
	public void ok_with_parameters() throws Exception {
		String text = "some text";
		QrWritter printer = new QrWritter();
		byte[] qrByte = printer.write(text, 265, 876);
		String decodeText = printer.readQR(qrByte, QrFormat.PNG);
		assertThat(decodeText).isEqualTo(text);
	}

	@Test
	public void ok_with_parameters_formatter_base_64() throws Exception {
		String text = "some text";
		QrWritter printer = new QrWritter();
		byte[] qrByte = printer.write(text, 265, 876, QrFormat.BASE64);
		String decodeText = printer.readQR(qrByte, QrFormat.BASE64);
		assertThat(decodeText).isEqualTo(text);
	}

	@Test
	public void ok_with_parameters_formatter_base_PNG() throws Exception {
		String text = "some text";
		QrWritter printer = new QrWritter();
		byte[] qrByte = printer.write(text, 265, 876, QrFormat.PNG);
		String decodeText = printer.readQR(qrByte, QrFormat.PNG);
		assertThat(decodeText).isEqualTo(text);
	}
}
