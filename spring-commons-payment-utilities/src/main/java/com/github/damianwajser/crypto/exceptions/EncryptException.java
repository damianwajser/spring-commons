package com.github.damianwajser.crypto.exceptions;

public class EncryptException extends Exception {

	public EncryptException(String message) {
		super(message);
	}

	public  EncryptException(String message, Exception e) {
		super(message, e);
	}

  
}
