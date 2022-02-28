package com.github.damianwajser.factories.jsonbased.conditions.operations;

import com.github.damianwajser.factories.jsonbased.conditions.operations.impl.EqualsStrategy;

public final class OperationFactory {

	private OperationFactory(){
		// private constructor
	}
	public static synchronized OperationStrategy getOperation(Operation operation) {
		return new EqualsStrategy();
	}

}
