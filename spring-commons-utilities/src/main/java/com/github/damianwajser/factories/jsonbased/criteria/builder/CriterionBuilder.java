package com.github.damianwajser.factories.jsonbased.criteria.builder;

import com.github.damianwajser.factories.jsonbased.conditions.Condition;
import com.github.damianwajser.factories.jsonbased.conditions.operations.Operation;
import com.github.damianwajser.factories.jsonbased.criteria.Criterion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class CriterionBuilder {

	private static final Pattern pattern = Pattern.compile("[\\$\\.A-z0-9]+|[\\=]");

	private CriterionBuilder(){
		//private constructor
	}

	public static <T> Criterion<T> build(String criterion, T result) {
		return new Criterion<>(getConditions(criterion), result);
	}

	private static Collection<Condition> getConditions(String criterion) {
		Collection<String> terms = Arrays.asList(criterion.split("AND"));
		return terms.stream().map(CriterionBuilder::getConditionByString).collect(Collectors.toList());
	}

	private static Condition getConditionByString(String t) {
		Matcher m = pattern.matcher(t);
		List<String> items = new ArrayList<>();
		while (m.find()) {
			items.add(m.group());
		}
		return new Condition(items.get(0), items.get(2), Operation.value(items.get(1)));
	}
}
