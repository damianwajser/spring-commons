package com.github.damianwajser.emv.parser;

import com.github.damianwajser.emv.exceptions.CrcValidationException;
import com.github.damianwajser.emv.exceptions.EmvFormatException;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EmvCoParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmvCoParser.class);

	private static final int LENGTH = 2;
	private static final String EMV_STARTED = "00";

	private EmvCoParser() {
	}

	public static Map<Integer, Object> parse(String str, boolean checkCrc) throws EmvFormatException {
		if (!checkCrc || isValidCrc(str)) {
			return convert(str);
		} else {
			throw new CrcValidationException("invalid crc verification, the code EMV is badly formed");
		}
	}

	public static Map<Integer, Object> parse(String str) throws EmvFormatException {
		return parse(str, true);
	}

	private static Map<Integer, Object> convert(String str) throws EmvFormatException {
		Map<Integer, Object> result = new HashMap<>();
		int i = 0;
		try {
			while (i < str.length()) {
				int tagInd = i + LENGTH;
				int tag = Integer.parseInt(str.substring(i, tagInd));
				int lengthValue = Integer.parseInt(str.substring(tagInd, tagInd + LENGTH));
				String value = str.substring(tagInd + LENGTH, tagInd + LENGTH + lengthValue);
				result.put(tag, getValue(value));
				i = tagInd + LENGTH + lengthValue;
			}
		} catch (NumberFormatException nfe) {
			throw new EmvFormatException(nfe.getMessage() + " - in field " + i, nfe);
		}
		return result;
	}

	private static Object getValue(String value) throws EmvFormatException {
		Object result = value;
		if (isObject(value)) {
			try {
				result = convert(value);
			} catch (StringIndexOutOfBoundsException sioe) {
				//donothing
				LOGGER.debug("parser", sioe);
			}
		}
		return result;
	}

	private static boolean isObject(String value) {
		int emvLength = EMV_STARTED.length();
		return value.startsWith(EMV_STARTED) &&
				value.length() > LENGTH &&
				NumberUtils.isCreatable(value.substring(emvLength, emvLength + 1));
	}

	private static boolean isValidCrc(String str) {
		return CrcHelper.isValidCrc(str);
	}
}

