package com.github.damianwajser.factories.jsonbased.criteria;

import java.util.ArrayList;
import java.util.Collection;

public class Criteria<T> {

	private Collection<Criterion<T>> criterionCollection;
	private T defaultResult;

	public Criteria() {
		this(null, null);
	}

	public Criteria(T defaultResult) {
		this(defaultResult, null);
	}

	public Criteria(Collection<Criterion<T>> criteria) {
		this(null, criteria);
	}

	public Criteria(T defaultResult, Collection<Criterion<T>> criteria) {
		this.defaultResult = defaultResult;
		this.criterionCollection = criteria != null ? criteria : new ArrayList<>();
	}

	public Collection<Criterion<T>> getAllCriterion() {
		return criterionCollection;
	}

	public T getDefaultResult() {
		return defaultResult;
	}

	public Criterion<T> addCriterion(Criterion<T> criterion) {
		this.criterionCollection.add(criterion);
		return criterion;
	}

	public Criterion<T> addCriterion(T defaultResult) {
		return this.addCriterion(new Criterion<>(defaultResult));
	}
}
