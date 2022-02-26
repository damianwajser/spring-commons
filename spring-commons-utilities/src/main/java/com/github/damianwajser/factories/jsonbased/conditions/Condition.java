package com.github.damianwajser.factories.jsonbased.conditions;

import com.github.damianwajser.factories.jsonbased.conditions.operations.Operation;
import com.github.damianwajser.factories.jsonbased.conditions.operations.OperationFactory;
import com.jayway.jsonpath.DocumentContext;

public class Condition {

	private String field;
	private String match;
	private Operation operation;

	public Condition(String field, String match) {
		this(field, match, Operation.EQUALS);
	}

	public Condition(String field, String match, Operation operation) {
		this.field = field;
		this.match = match;
		this.operation = operation;
	}

	public boolean match(DocumentContext json) {
		Object value = json.read(field).toString();
		return OperationFactory.getOperation(operation).apply(value, match);
	}

	@Override
	public String toString() {
		return "Condition("+ field + " " + operation + " " + match + ')';
	}
}
