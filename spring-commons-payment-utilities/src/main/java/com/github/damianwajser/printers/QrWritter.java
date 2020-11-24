package com.github.damianwajser.printers;

import com.github.damianwajser.printers.formatters.QrFormat;
import com.github.damianwajser.printers.formatters.impl.FormattersFactory;
import com.google.zxing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class QrWritter {

	private static final Logger LOGGER = LoggerFactory.getLogger(QrWritter.class);

	private static final Integer WIDTH_DEFAULT = 512;
	private static final Integer HEIGHT_DEFAULT = 512;


	public byte[] write(String text, int width, int height, QrFormat format) throws WriterException, IOException {
		width = (width > 0) ? width : WIDTH_DEFAULT;
		height = (height > 0) ? height : HEIGHT_DEFAULT;
		return FormattersFactory.getFormatter(format).write(text, width, height);
	}

	public byte[] write(String code) throws WriterException, IOException {
		return write(code, HEIGHT_DEFAULT, HEIGHT_DEFAULT);
	}

	public byte[] write(String code, int width, int height) throws WriterException, IOException {
		return write(code, width, height, QrFormat.PNG);
	}

	// Function to read the QR file
	public String readQR(byte[] qr, QrFormat format) throws NotFoundException, IOException {
		return FormattersFactory.getFormatter(format).read(qr);
	}
}
