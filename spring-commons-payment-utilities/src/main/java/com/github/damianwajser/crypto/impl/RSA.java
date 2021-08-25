package com.github.damianwajser.crypto.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.github.damianwajser.crypto.ICrypto;


public class RSA  implements ICrypto {
    static final String KEY_ALGORITHM = "RSA";
    static final int KEY_LENGTH = 2048;
  
    public String Encrypt(PublicKey secretKey, String plainText)
    throws NoSuchAlgorithmException,
    InvalidKeySpecException,
    NoSuchPaddingException,
    InvalidKeyException, 
    InvalidAlgorithmParameterException,
    UnsupportedEncodingException,
    IllegalBlockSizeException,
    BadPaddingException,
    IOException {
           
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        
        //Initializing the key pair generator
        keyPairGen.initialize(KEY_LENGTH);
        
        //Getting the public key from the key pair
        PublicKey publicKey = secretKey; 
  
        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
  
        //Initializing a Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        //Add data to the cipher
        byte[] input = plainText.getBytes();	  
        cipher.update(input);
        
        //encrypting the data
        byte[] cipherText = cipher.doFinal();
        return new String(cipherText, "UTF8");
    }
  
    public String Decrypt(PrivateKey secretKey, String plainText) 
    throws NoSuchAlgorithmException,
    InvalidKeySpecException,
    NoSuchPaddingException,
    InvalidKeyException,
    InvalidAlgorithmParameterException,
    UnsupportedEncodingException, 
    IllegalBlockSizeException,
    BadPaddingException, 
    IOException {

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        
        //Initializing the key pair generator
        keyPairGen.initialize(2048);        
         
        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
  
        //Initializing a Cipher object
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        
        //Add data to the cipher
        byte[] input = plainText.getBytes();	  
        cipher.update(input);
        
        //encrypting the data
        byte[] cipherText = cipher.doFinal();
        return new String(cipherText, "UTF8");
    }

    private PublicKey GetPublicKeyfromString(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    private PrivateKey GetPrivateKeyfromString(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public String Encryption(String key, String textPlain) {

        String encriptResult="";
        PublicKey publicKey =GetPublicKeyfromString(key);
        try {
            encriptResult=  Encrypt(publicKey, textPlain);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encriptResult;
    }

    public String Descryption(String key, String textPlain) {  
        String encriptResult="";
        PrivateKey publicKey =GetPrivateKeyfromString(key);
        try {
            encriptResult=  Decrypt(publicKey, textPlain);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encriptResult;
    }
  
    public HashMap<String, String> Getkey() {
        HashMap<String, String> arraykey = new HashMap<String, String>();

        try {
       
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        
        //Initializing the key pair generator
        keyPairGen.initialize(KEY_LENGTH);
    
        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();   
        
        //Getting the public key from the key pair
        PublicKey publicKey = pair.getPublic();  
        PrivateKey privateKey = pair.getPrivate();

        RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)privateKey;
        RSAPublicKey rSAPublicKey = (RSAPublicKey)publicKey;
        byte[] mod_Bytes_Extra =null;

        BigInteger mod_IntPublic = rSAPublicKey.getModulus();
        BigInteger exp_IntPublic = rSAPublicKey.getPublicExponent();


        mod_Bytes_Extra = mod_IntPublic.toByteArray();
        byte[] mod_Bytes = new byte[128];

        System.arraycopy(mod_Bytes_Extra, 1, mod_Bytes, 0, 128);


        byte[] exp_Bytes = exp_IntPublic.toByteArray();
        String modulus =Base64.getEncoder().encodeToString(mod_Bytes);
        String exponent = Base64.getEncoder().encodeToString(exp_Bytes);
        String public_Xml = "<BitStrength>0124</BitStrength><RSAKeyValue><Modulus>"+modulus+"</Modulus><Exponent>"+exponent+"</Exponent></RSAKeyValue>";
		
		arraykey.put("PublicKey",Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        arraykey.put("Privatekey",Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        arraykey.put("public_Xml", public_Xml);
        //Creating a Cipher object
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return arraykey;
    }



public static void main(String[] args) throws Exception {
    RSA rsa = new RSA();   
    System.out.println(rsa.Getkey());

}

    
}
