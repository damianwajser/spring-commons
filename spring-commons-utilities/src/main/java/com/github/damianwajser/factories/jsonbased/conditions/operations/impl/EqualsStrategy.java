package com.github.damianwajser.factories.jsonbased.conditions.operations.impl;

import com.github.damianwajser.factories.jsonbased.conditions.operations.OperationStrategy;

public class EqualsStrategy implements OperationStrategy {
	@Override
	public boolean apply(Object source, Object target) {
		return source.equals(target);
	}
}
