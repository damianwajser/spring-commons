package com.github.damianwajser.printers.formatters;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface Formatter {
	byte[] write(String text, int width, int height) throws WriterException, IOException;

	String read(byte[] qr) throws NotFoundException, IOException;
}
