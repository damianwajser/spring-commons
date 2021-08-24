package com.github.damianwajser.crypto;

public interface ICrypto {
   public String Encription( String key, String textPlain);
   public String Description( String key, String textPlain);
   public String Getkey(Keytype keytype); 
}
