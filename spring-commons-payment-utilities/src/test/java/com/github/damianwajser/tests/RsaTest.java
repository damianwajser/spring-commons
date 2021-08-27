package com.github.damianwajser.tests;

import com.github.damianwajser.crypto.impl.RSA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.assertThat;
//TODO create test: XML, PrivateKey descrytion.
@RunWith(SpringJUnit4ClassRunner.class)
public class RsaTest {
    @Test
	public void ok() throws Exception {
        RSA app = new RSA();
        String plainText = "Plain text which need to be encrypted by Java RSA Encryption in ECB Mode";
        byte[] cipherTextArray = app.EncryptToByteArray(plainText,app.GetPublicKey() );
        String decryptedText = app.DecryptFromBytes(cipherTextArray, app.GetPrivateKey() );       
		assertThat(decryptedText).isEqualTo(plainText);
	} 
    
}