package com.github.damianwajser.printers.formatters.impl;

import com.github.damianwajser.printers.formatters.Formatter;
import com.github.damianwajser.printers.formatters.QrFormat;

public class FormattersFactory {

	public static Formatter getFormatter(QrFormat format) {
		Formatter formatter = null;
		switch (format) {
			case BASE64:
				formatter = new Base64Formatter();
				break;
			default:
				formatter = new DefaultFormatter();
				break;
		}
		return formatter;
	}
}
