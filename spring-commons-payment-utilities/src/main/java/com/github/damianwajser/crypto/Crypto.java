package com.github.damianwajser.crypto;

public class Crypto  {

     private ICrypto icrypto = null;
    
    public String Encription(String key, String textPlain, CryptoFormat cryptoFormat) {
        icrypto= CryptoFactory.getFormatter(cryptoFormat);
        
        return icrypto.Encryption(key, textPlain);
    }


    public String Description(String key, String textPlain, CryptoFormat cryptoFormat) {
        icrypto= CryptoFactory.getFormatter(cryptoFormat);
        return icrypto.Encryption(key, textPlain);
    }

    public String Getkey(CryptoFormat cryptoFormat) {
        icrypto= CryptoFactory.getFormatter(cryptoFormat);
        return icrypto.toString();
    }

    
}
