package com.github.damianwajser.crypto;

import com.github.damianwajser.crypto.impl.PBE;
import com.github.damianwajser.crypto.impl.RSA;

public class CryptoFactory {
    public static ICrypto getFormatter(CryptoFormat format) {
		ICrypto formatter = null;
		switch (format) {
			case RSA:
				formatter =  new RSA();
				break;
			default:
				formatter = new PBE();
				break;
		}
		return formatter;
	}
}
