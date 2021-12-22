package com.github.damianwajser.tests;

import com.github.damianwajser.crypto.CryptoUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptoTest {

	@Test
	public void crypto() throws Exception {
		CryptoUtil cryptoUtil = new CryptoUtil();
		String key = "123";
		String plain = "1";
		String enc = cryptoUtil.encrypt(key, plain);
		assertThat(cryptoUtil.decrypt(key, enc)).isEqualTo(plain);
	}
}
