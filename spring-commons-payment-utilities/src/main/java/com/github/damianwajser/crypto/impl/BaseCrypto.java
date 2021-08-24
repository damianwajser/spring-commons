package com.github.damianwajser.crypto.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public abstract class BaseCryto {
  
    public abstract String Encrypt(String secretKey, String plainText)
    throws NoSuchAlgorithmException,
    InvalidKeySpecException,
    NoSuchPaddingException,
    InvalidKeyException,
    InvalidAlgorithmParameterException,
    UnsupportedEncodingException,
    IllegalBlockSizeException,
    BadPaddingException,
    IOException;

    public abstract String Decrypt(String secretKey, String plainText)
    throws NoSuchAlgorithmException,
    InvalidKeySpecException,
    NoSuchPaddingException,
    InvalidKeyException,
    InvalidAlgorithmParameterException,
    UnsupportedEncodingException,
    IllegalBlockSizeException,
    BadPaddingException,
    IOException; 

    
}
