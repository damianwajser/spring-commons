package com.github.damianwajser.factories.jsonbased.conditions.operations;

import java.util.HashMap;
import java.util.Map;

public enum Operation {
	EQUALS("=");

	private static final Map<String, Operation> BY_OPERATION = new HashMap<>();

	private final String value;

	static {
		for (Operation e : values()) {
			BY_OPERATION.put(e.value, e);
		}
	}

	Operation(String value) {
		this.value = value;
	}

	public static Operation value(String label) {
		return BY_OPERATION.get(label);
	}
}
