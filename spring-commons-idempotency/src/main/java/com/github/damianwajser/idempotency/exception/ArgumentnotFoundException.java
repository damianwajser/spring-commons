package com.github.damianwajser.idempotency.exception;

public class ArgumentnotFoundException extends RuntimeException {

	private String argument;

	public ArgumentnotFoundException(String argument) {
		this.argument = argument;
	}

	public String getArgument() {
		return argument;
	}
}
