package com.github.damianwajser.idempotency.exception;

public class ArgumentNotFoundException extends RuntimeException {

	private final String argument;

	public ArgumentNotFoundException(String argument) {
		this.argument = argument;
	}

	public String getArgument() {
		return argument;
	}
}
