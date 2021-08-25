package com.github.damianwajser.crypto;

import java.util.HashMap;

public interface ICrypto {
   public String Encryption(String key, String textPlain);
   public String Descryption( String key, String textPlain);
   public HashMap<String, String> Getkey(); 
}
