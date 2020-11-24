package com.github.damianwajser.printers.formatters.impl;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.springframework.util.Base64Utils;

import java.io.IOException;

public class Base64Formatter extends DefaultFormatter {
	@Override
	public byte[] write(String text, int width, int height) throws WriterException, IOException {
		return Base64Utils.encode(super.write(text, width, height));
	}

	@Override
	public String read(byte[] qr) throws NotFoundException, IOException {
		byte[] decodedBytes = Base64Utils.decode(qr);
		return super.read(decodedBytes);
	}
}
