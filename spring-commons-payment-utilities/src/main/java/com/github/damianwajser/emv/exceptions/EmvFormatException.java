package com.github.damianwajser.emv.exceptions;

public class EmvFormatException extends Exception {

	public EmvFormatException(String message) {
		super(message);
	}

	public EmvFormatException(String message, Exception e) {
		super(message, e);
	}
}
