package com.github.damianwajser.factories.jsonbased.criteria;

import com.github.damianwajser.factories.jsonbased.conditions.Condition;
import com.jayway.jsonpath.DocumentContext;

import java.util.ArrayList;
import java.util.Collection;

public class Criteria<T> {

	private Collection<Condition> conditions;
	private T result;

	public Criteria(T result) {
		this(null, result);
	}

	public Criteria(Collection<Condition> conditions, T result) {
		this.conditions = conditions != null ? conditions : new ArrayList<>();
		this.result = result;
	}

	public Criteria<T> and(Condition condition) {
		this.conditions.add(condition);
		return this;
	}

	public T getResult() {
		return this.result;
	}

	public boolean match(DocumentContext json) {
		return this.conditions.stream().allMatch(c->c.match(json));
	}

}
