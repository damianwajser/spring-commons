package com.github.damianwajser.crypto.impl;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.github.damianwajser.crypto.CryptoFormat;
import com.github.damianwajser.crypto.ICrypto;
import com.github.damianwajser.crypto.Keytype;

import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class PBE  implements ICrypto {


	Cipher ecipher;
	Cipher dcipher;
	// 8-byte Salt
	byte[] salt = {
			(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
	};
	// Iteration count
	int iterationCount = 19;

	/**
	 * @param secretKey Key used to encrypt data
	 * @param plainText Text input to be encrypted
	 * @return Returns encrypted text
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.spec.InvalidKeySpecException
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws java.security.InvalidKeyException
	 * @throws java.security.InvalidAlgorithmParameterException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.BadPaddingException
	 */

	public String Encrypt(String secretKey, String plainText)
			throws NoSuchAlgorithmException,
			InvalidKeySpecException,
			NoSuchPaddingException,
			InvalidKeyException,
			InvalidAlgorithmParameterException,
			UnsupportedEncodingException,
			IllegalBlockSizeException,
			BadPaddingException {
		//Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

		//Enc process
		ecipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		String charSet = "UTF-8";
		byte[] in = plainText.getBytes(charSet);
		byte[] out = ecipher.doFinal(in);
		String encStr = new String(Base64.getEncoder().encode(out));
		return encStr;
	}

	/**
	 * @param secretKey     Key used to decrypt data
	 * @param encryptedText encrypted text input to decrypt
	 * @return Returns plain text after decryption
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.spec.InvalidKeySpecException
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws java.security.InvalidKeyException
	 * @throws java.security.InvalidAlgorithmParameterException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.BadPaddingException
	 */

	public String Decrypt(String secretKey, String encryptedText)
			throws NoSuchAlgorithmException,
			InvalidKeySpecException,
			NoSuchPaddingException,
			InvalidKeyException,
			InvalidAlgorithmParameterException,
			UnsupportedEncodingException,
			IllegalBlockSizeException,
			BadPaddingException,
			IOException {
		//Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
		//Decryption process; same key will be used for decr
		dcipher = Cipher.getInstance(key.getAlgorithm());
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		byte[] enc = Base64.getDecoder().decode(encryptedText);
		byte[] utf8 = dcipher.doFinal(enc);
		String charSet = "UTF-8";
		String plainStr = new String(utf8, charSet);
		return plainStr;
	}

	public static void main(String[] args) throws Exception {
	
		PBE cryptoUtil = new PBE();
		String key = "1234";
		String plain = "1";
		String enc = cryptoUtil.Encrypt(key, plain);
		System.out.println("Original text: " + plain);
		System.out.println("Encrypted text: " + enc);
		String plainAfter = cryptoUtil.Decrypt(key, enc);
		System.out.println("Original text after decryption: " + plainAfter);
	}

	public String Encryption(String key, String textPlain) {
		String result ="";
		try {
			result =Encrypt(key, textPlain);
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
		}

		return result;
	}

	public HashMap<String, String> Getkey() {
		HashMap<String, String> arraykey = new HashMap<String, String>();
		UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
		arraykey.put("Key",uuidAsString);

		return arraykey;
	}

	public String Descryption(String key, String textPlain) {
		String result = "";
		try {
			result=Decrypt(key, textPlain);
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
		return result;
	}





}
