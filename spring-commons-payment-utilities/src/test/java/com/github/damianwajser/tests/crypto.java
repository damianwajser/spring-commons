package com.github.damianwajser.tests;

import com.github.damianwajser.crypto.exceptions.EncryptException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@RunWith(SpringJUnit4ClassRunner.class)
public class crypto {
    @Test()
	public void invalidCRCAndParse() throws Exception {
		String plaintext = "hello world";
		String value = "hello world";
//	Map<Integer, Object> emv = EmvCoParser.parse(qr, true);
        failBecauseExceptionWasNotThrown(EncryptException.class);
	}
}
