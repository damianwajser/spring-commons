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
  
   private PublicKey publicKey;
   private PrivateKey privateKey;
   static final String KEY_ALGORITHM = "RSA";
   static final int KEY_LENGTH = 2048;

    public RSA() 
    throws NoSuchAlgorithmException{
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
    keyGen.initialize(KEY_LENGTH);
    KeyPair pair = keyGen.generateKeyPair();
    this.privateKey = pair.getPrivate();
    this.publicKey = pair.getPublic();
    }public PublicKey GetPublickeyFromString(String base64PublicKey){
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
    }    public PrivateKey GetPrivatekeyFromString(String base64PrivateKey)
        throws NoSuchAlgorithmException,
        InvalidKeySpecException,
        InvalidKeySpecException{
        base64PrivateKey=base64PrivateKey
        .replace("-----BEGIN PRIVATE KEY-----\n", "")
        .replace("\n-----END PRIVATE KEY-----", "");
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;       
        keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        privateKey = keyFactory.generatePrivate(keySpec);
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
        String modulus =GetBase64(mod_Bytes);
        String exponent =GetBase64(exp_Bytes);
        public_Xml = "<RSAKeyValue><Modulus>"+modulus+"</Modulus><Exponent>"+exponent+"</Exponent></RSAKeyValue>";
      return  public_Xml;      
    }
    public String GetPrivateKeyAsXml()
        throws Exception{   
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateCrtKeySpec spec = keyFactory.getKeySpec(GetPrivateKey(), RSAPrivateCrtKeySpec.class);
        StringBuilder sb = new StringBuilder();
        sb.append("<RSAKeyValue>");
        sb.append(GetElement("Modulus", spec.getModulus()));
        sb.append(GetElement("Exponent", spec.getPublicExponent()));
        sb.append(GetElement("P", spec.getPrimeP()));
        sb.append(GetElement("Q", spec.getPrimeQ()));
        sb.append(GetElement("DP", spec.getPrimeExponentP()));
        sb.append(GetElement("DQ", spec.getPrimeExponentQ()));
        sb.append(GetElement("InverseQ", spec.getCrtCoefficient()));
        sb.append(GetElement("D", spec.getPrivateExponent()));
        sb.append("</RSAKeyValue>");
        return sb.toString();
    }
    public String GetPrivatekeyAsString() {
       return Base64.getMimeEncoder().encodeToString(GetPrivateKey().getEncoded());
    }
    public String GetPublickeyAsString() {
        return Base64.getMimeEncoder().encodeToString(GetPublicKey().getEncoded());
    }      
    public byte[] EncryptToByteArray(String plainText,PublicKey publicKey )
       throws Exception{
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");        
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);        
        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes()) ;

        return cipherText;
    }
    public String EncryptToString (String plainText,PublicKey publicKey )
       throws Exception{
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");        
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);        
        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes()) ;
       
        return  Base64.getMimeEncoder().encodeToString(cipherText);
    }
    public String DecryptFromBytes(byte[] cipherTextArray, PrivateKey privateKey)
    throws Exception{
        
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);        
        return new String(decryptedTextArray);
    }
    public String DecryptFromString(String cipherTextArray, PrivateKey privateKey)
    throws Exception{
        
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray.getBytes());        
        return new String(decryptedTextArray);
    }
    private  String GetBase64(byte[] bytes){
        return Base64.getMimeEncoder().encodeToString(bytes);
    }
    private  String GetElement(String name, BigInteger bigInt) throws Exception {
        byte[] bytesFromBigInt = GetBytesFromBigInt(bigInt);
        String elementContent = GetBase64(bytesFromBigInt);
        return String.format("  <%s>%s</%s>", name, elementContent, name);
    }
    private  byte[] GetBytesFromBigInt(BigInteger bigInt){
        byte[] bytes = bigInt.toByteArray();
        int length = bytes.length;

        if(length % 2 != 0 && bytes[0] == 0) {
            bytes = Arrays.copyOfRange(bytes, 1, length);
        }

        return bytes;
    }public static void main(String[] args) throws Exception {
    }
}