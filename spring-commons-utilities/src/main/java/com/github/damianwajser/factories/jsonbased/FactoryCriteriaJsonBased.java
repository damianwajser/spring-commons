package com.github.damianwajser.factories.jsonbased;

import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class FactoryCriteriaJsonBased<T> {

	public Collection<Criteria<T>> criterias;
	public T defaultResult;

	public FactoryCriteriaJsonBased(Collection<Criteria<T>> criterias) {
		this(null, criterias);
	}

	public FactoryCriteriaJsonBased(T defaultResult, Collection<Criteria<T>> criterias) {
		this.defaultResult = defaultResult;
		this.criterias = criterias != null ? criterias : new ArrayList<>();
	}

	public Collection<T> applyCriteria(DocumentContext json) {
		Collection<T> result = criterias.stream().filter(c -> c.match(json)).map(c -> c.getResult()).collect(Collectors.toList());
		if (result.isEmpty() && defaultResult != null) {
			result.add(defaultResult);
		}
		return result;
	}

	public Collection<T> applyCriteria(String json) {
		return this.applyCriteria(JsonPath.parse(json));
	}
}
