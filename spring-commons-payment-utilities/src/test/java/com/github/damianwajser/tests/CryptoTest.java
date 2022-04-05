package com.github.damianwajser.tests;

import com.github.damianwajser.crypto.CryptoUtil;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CryptoTest {

	@Test
	public void crypto() throws Exception {
		CryptoUtil cryptoUtil = new CryptoUtil();
		String key = "123";
		String plain = "1";
		String enc = cryptoUtil.encrypt(key, plain);
		assertThat(cryptoUtil.decrypt(key, enc)).isEqualTo(plain);
	}

	@Test
	public void cryptoAll() throws Exception {
		CryptoUtil cryptoUtil = new CryptoUtil();
		String key = "123";
		String plain = "1";
		cryptoUtil.getAlgorithms().forEach(a->{
			try {
				assertThat(cryptoUtil.decrypt(key, cryptoUtil.encrypt(key, plain))).isEqualTo(plain);
			} catch (Exception e) {
				e.printStackTrace();
				fail("exception",e);
			}
		});
	}

}
