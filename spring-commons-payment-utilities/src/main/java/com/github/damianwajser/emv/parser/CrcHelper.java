package com.github.damianwajser.emv.parser;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

final class CrcHelper {

	private static final String FORMATTER = "%04X";
	private static final int SEVEN_BITES = 7;
	private static final int ONE_BITES = 1;
	private static final int FIFTEEN_BITES = 15;
	private static final int CRC_LENGTH = 4;

	private CrcHelper() {
	}

	public static boolean isValidCrc(String qrCode) {
		String content = qrCode.substring(0, qrCode.length() - CRC_LENGTH);
		String providedCRC = qrCode.substring(qrCode.length() - CRC_LENGTH).toUpperCase();
		String generatedCRC = generateCrc16(content, 0xFFFF, 0x1021);
		return providedCRC.equals(generatedCRC);
	}

	private static String generateCrc16(String data, int preset, int polynomial) {
		int crc = preset;
		byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
		for (byte aByte : bytes) {
			for (int i = 0; i <= SEVEN_BITES; ++i) {
				boolean bit = (aByte >> SEVEN_BITES - i & ONE_BITES) == ONE_BITES;
				boolean c15 = (crc >> FIFTEEN_BITES & ONE_BITES) == ONE_BITES;
				crc <<= ONE_BITES;
				if (c15 ^ bit) {
					crc ^= polynomial;
				}
			}
		}
		crc &= preset;
		Object[] var18 = new Object[]{crc};
		return String.format(FORMATTER, Arrays.copyOf(var18, var18.length));
	}
}
