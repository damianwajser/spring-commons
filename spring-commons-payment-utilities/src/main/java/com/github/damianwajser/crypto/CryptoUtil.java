package com.github.damianwajser.crypto;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

public class CryptoUtil {

	private static final String CIPHER_SERVICE_NAME = "Cipher";
	private static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDES";

	private Cipher ecipher;
	private Cipher dcipher;
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
	public String encrypt(String secretKey, String plainText)
			throws NoSuchAlgorithmException,
			InvalidKeySpecException,
			NoSuchPaddingException,
			InvalidKeyException,
			InvalidAlgorithmParameterException,
			UnsupportedEncodingException,
			IllegalBlockSizeException,
			BadPaddingException {
		return encrypt(secretKey, plainText, DEFAULT_ALGORITHM);
	}
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
	public String encrypt(String secretKey, String plainText, String algorithm)
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
		SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

		//Enc process
		ecipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		String charSet = "UTF-8";
		byte[] in = plainText.getBytes(charSet);
		byte[] out = ecipher.doFinal(in);
		return new String(Base64.getEncoder().encode(out));
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
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.BadPaddingException
	 */
	public String decrypt(String secretKey, String encryptedText)
			throws NoSuchAlgorithmException,
			InvalidKeySpecException,
			NoSuchPaddingException,
			InvalidKeyException,
			InvalidAlgorithmParameterException,
			IllegalBlockSizeException,
			BadPaddingException {
		return decrypt(secretKey,encryptedText, DEFAULT_ALGORITHM);
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
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.BadPaddingException
	 */
	public String decrypt(String secretKey, String encryptedText, String algorithm)
			throws NoSuchAlgorithmException,
			InvalidKeySpecException,
			NoSuchPaddingException,
			InvalidKeyException,
			InvalidAlgorithmParameterException,
			IllegalBlockSizeException,
			BadPaddingException {
		//Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
		//Decryption process; same key will be used for decr
		dcipher = Cipher.getInstance(key.getAlgorithm());
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		byte[] enc = Base64.getDecoder().decode(encryptedText);
		byte[] utf8 = dcipher.doFinal(enc);
		return new String(utf8, StandardCharsets.UTF_8);
	}

	public Collection<String> getAlgorithms(){
		return Arrays.stream(Security.getProviders())
				.flatMap(provider -> provider.getServices().stream())
				.filter(service -> CIPHER_SERVICE_NAME.equals(service.getType()))
				.map(Provider.Service::getAlgorithm)
				.collect(Collectors.toList());
	}
}
