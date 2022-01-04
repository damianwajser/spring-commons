package com.github.damianwajser.printers.formatters.impl;

import com.github.damianwajser.printers.formatters.Formatter;
import com.github.damianwajser.printers.formatters.QrFormat;

public class FormattersFactory {

	private FormattersFactory() {
	}

	public static Formatter getFormatter(QrFormat format) {
		return format.equals(QrFormat.BASE64) ? new Base64Formatter() : new DefaultFormatter();
	}
}
