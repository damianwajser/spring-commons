package com.github.damianwajser.factories.jsonbased.conditions.operations;

public interface OperationStrategy {
	boolean apply(Object source, Object target);
}
