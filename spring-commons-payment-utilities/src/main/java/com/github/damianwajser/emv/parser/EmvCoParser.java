package com.github.damianwajser.emv.parser;

import com.github.damianwajser.emv.exceptions.CrcValidationException;

import java.util.HashMap;
import java.util.Map;

public class EmvCoParser {

	private static final int LENGTH = 2;
	private static final String EMV_STARTED = "00";

	public static Map<Integer, Object> parse(String str) throws CrcValidationException {
		if (isValidCrc(str)) {
			return convert(str);
		} else {
			throw new CrcValidationException("invalid crc verification, the code EMV is badly formed");
		}
	}

	private static Map<Integer, Object> convert(String str) throws CrcValidationException {
		Map<Integer, Object> result = new HashMap<>();
		int i = 0;
		while (i < str.length()) {
			int tagInd = i + LENGTH;
			int tag = Integer.parseInt(str.substring(i, tagInd));
			int lengthValue = Integer.parseInt(str.substring(tagInd, tagInd + LENGTH));
			String value = str.substring(tagInd + LENGTH, tagInd + LENGTH + lengthValue);
			if (value.startsWith(EMV_STARTED) && value.length() > LENGTH) {
				result.put(tag, convert(value));
			} else {
				result.put(tag, value);
			}
			i = tagInd + LENGTH + lengthValue;
		}
		return result;
	}

	private static boolean isValidCrc(String str) {
		return CrcHelper.isValidCrc(str);
	}
}

