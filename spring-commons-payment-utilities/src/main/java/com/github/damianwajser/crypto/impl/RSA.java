package com.github.damianwajser.crypto.impl;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;

import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;





import java.util.Arrays;


public class RSA {    
  
   PublicKey publicKey;
   PrivateKey privateKey;
   static final String KEY_ALGORITHM = "RSA";
   static final int KEY_LENGTH = 2048;

   public RSA() 
   throws NoSuchAlgorithmException{
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
    keyGen.initialize(KEY_LENGTH);
    KeyPair pair = keyGen.generateKeyPair();
    this.privateKey = pair.getPrivate();
    this.publicKey = pair.getPublic();
    }


    /*
    GetPublickeyFromString
    
    */
    
    public PublicKey GetPublickeyFromString(String base64PublicKey){
        base64PublicKey=base64PublicKey
        .replace("-----BEGIN PUBLIC KEY-----\n", "")
        .replace("\n-----END PUBLIC KEY-----", "");
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getMimeDecoder().decode(base64PublicKey.getBytes()));
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
    public PrivateKey GetPrivatekeyFromString(String base64PrivateKey){
        base64PrivateKey=base64PrivateKey
        .replace("-----BEGIN PRIVATE KEY-----\n", "")
        .replace("\n-----END PRIVATE KEY-----", "");

        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(base64PrivateKey.getBytes()));
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
    public PrivateKey GetPrivateKey() {
        return privateKey;
    }
    public PublicKey GetPublicKey() {
        return publicKey;
    }


    public String GetPublickeyAsXml(){
        String public_Xml ="";
    
        RSAPublicKey rSAPublicKey = (RSAPublicKey) GetPublicKey();
        byte[] mod_Bytes_Extra =null;

        BigInteger mod_IntPublic = rSAPublicKey.getModulus();
        BigInteger exp_IntPublic = rSAPublicKey.getPublicExponent();


        mod_Bytes_Extra = mod_IntPublic.toByteArray();
        byte[] mod_Bytes = new byte[256];
        System.arraycopy(mod_Bytes_Extra, 1, mod_Bytes, 0, 256);


        byte[] exp_Bytes = exp_IntPublic.toByteArray();
        String modulus =getBase64(mod_Bytes);
        String exponent =getBase64(exp_Bytes);
        public_Xml = "<RSAKeyValue><Modulus>"+modulus+"</Modulus><Exponent>"+exponent+"</Exponent></RSAKeyValue>";
      return  public_Xml;
      //  System.out.println(GetPrivateKeyAsXml(privateKey));
    }
    public String GetPrivateKeyAsXml() throws Exception{
    

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateCrtKeySpec spec = keyFactory.getKeySpec(GetPrivateKey(), RSAPrivateCrtKeySpec.class);
        StringBuilder sb = new StringBuilder();

        sb.append("<RSAKeyValue>");
        sb.append(getElement("Modulus", spec.getModulus()));
        sb.append(getElement("Exponent", spec.getPublicExponent()));
        sb.append(getElement("P", spec.getPrimeP()));
        sb.append(getElement("Q", spec.getPrimeQ()));
        sb.append(getElement("DP", spec.getPrimeExponentP()));
        sb.append(getElement("DQ", spec.getPrimeExponentQ()));
        sb.append(getElement("InverseQ", spec.getCrtCoefficient()));
        sb.append(getElement("D", spec.getPrivateExponent()));
        sb.append("</RSAKeyValue>");

        return sb.toString();
    }
    public String GetPrivatekeyAsString() {
       return Base64.getMimeEncoder().encodeToString(GetPrivateKey().getEncoded());
    }
    public String GetPublickeyAsString() {
        return Base64.getMimeEncoder().encodeToString(GetPublicKey().getEncoded());
    }
      
    public byte[] encrypt (String plainText,PublicKey publicKey ) throws Exception
    {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes()) ;

        return cipherText;
    }
    public String decrypt (byte[] cipherTextArray, PrivateKey privateKey) throws Exception
    {
        
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);
        
        return new String(decryptedTextArray);
    }

    private  String getBase64(byte[] bytes){
        return Base64.getMimeEncoder().encodeToString(bytes);
    }
    private  String getElement(String name, BigInteger bigInt) throws Exception {
        byte[] bytesFromBigInt = getBytesFromBigInt(bigInt);
        String elementContent = getBase64(bytesFromBigInt);
        return String.format("  <%s>%s</%s>", name, elementContent, name);
    }
    private  byte[] getBytesFromBigInt(BigInteger bigInt){
        byte[] bytes = bigInt.toByteArray();
        int length = bytes.length;

        if(length % 2 != 0 && bytes[0] == 0) {
            bytes = Arrays.copyOfRange(bytes, 1, length);
        }

        return bytes;
    }
 

    public static void main(String[] args) throws Exception {
     
        RSA app = new RSA();
  
        
        String plainText = "Plain text which need to be encrypted by Java RSA Encryption in ECB Mode";
        System.out.println ("-----BEGIN PRIVATE KEY-----");
        System.out.println (app.GetPrivatekeyAsString());
        System.out.println ("-----END PRIVATE KEY-----");

        System.out.println ("-----BEGIN PUBLIC KEY-----");
        System.out.println (app.GetPublickeyAsString());
        System.out.println ("-----END PUBLIC KEY-----");

      
        System.out.println (app.GetPrivateKeyAsXml());
        System.out.println (app.GetPublickeyAsXml());
        System.out.println("Original Text  : "+plainText);

        PublicKey publickey =  app.GetPublickeyFromString(app.GetPublickeyAsString());
        PrivateKey privatekey =  app.GetPrivatekeyFromString(app.GetPrivatekeyAsString());

        byte[] cipherTextArray = app.encrypt(plainText,publickey );
        String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
        System.out.println("Encrypted Text : "+encryptedText);
        
     
        String decryptedText = app.decrypt(cipherTextArray, privatekey );
        System.out.println("DeCrypted Text : "+decryptedText);
    }

}