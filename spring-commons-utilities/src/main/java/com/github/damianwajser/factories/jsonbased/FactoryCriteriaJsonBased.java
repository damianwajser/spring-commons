package com.github.damianwajser.factories.jsonbased;

import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.criteria.Criterion;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.Collection;
import java.util.stream.Collectors;

public class FactoryCriteriaJsonBased<T> {

	public Criteria<T> criteria;

	public FactoryCriteriaJsonBased(Criteria<T> criteria) {
		this.criteria = criteria;
	}

	public Collection<T> find(DocumentContext json) {
		Collection<T> result = criteria.getAllCriterion().stream()
				.filter(c -> c.match(json))
				.map(Criterion::getResult)
				.collect(Collectors.toList());
		if (result.isEmpty() && criteria.getDefaultResult() != null) {
			result.add(criteria.getDefaultResult());
		}
		return result;
	}

	public Collection<T> find(String json) {
		return this.find(JsonPath.parse(json));
	}
}
