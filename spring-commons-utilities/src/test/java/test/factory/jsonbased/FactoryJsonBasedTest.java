package test.factory.jsonbased;

import com.github.damianwajser.factories.jsonbased.conditions.Condition;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class FactoryJsonBasedTest {

	@Test
	public void returnDefaultNull() {
		FactoryCriteriaJsonBased<Object> factory = new FactoryCriteriaJsonBased<>(new ArrayList<>());
		assertThat(factory.applyCriteria(getJson()), empty());
	}

	@Test
	public void returnDefaultString() {
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", new ArrayList<>());
		TestUtils.checkResult(factory.applyCriteria(getJson()), "default");
	}

	@Test
	public void returnMatchStringOneMatch() {
		Criteria<String> criteria = new Criteria<String>("damianResult");
		criteria.and(new Condition("$.origin","damian"));
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatches() {
		Criteria<String> criteria = new Criteria<String>("damianResult");
		criteria.and(new Condition("$.origin","damian"));
		criteria.and(new Condition("$.amount","5"));
		criteria.and(new Condition("$.destination","owen"));
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatchesOneNoMatchAndReturnDefault() {
		Criteria<String> criteria = new Criteria<String>("damianResult");
		criteria.and(new Condition("$.origin","damian"));
		criteria.and(new Condition("$.amount","5"));
		criteria.and(new Condition("$.destination","other"));
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "default");
	}

	@Test
	public void returnMatchStringManyMatchesManyCriteriaOneNoMatchAndReturnDefault() {
		Criteria<String> criteria = new Criteria<String>("criteriaResult");
		criteria.and(new Condition("$.origin","damian"));
		criteria.and(new Condition("$.amount","5"));
		criteria.and(new Condition("$.destination","owen"));

		Criteria<String> criteriaTwo = new Criteria<String>("criteriaTwoResult");
		criteria.and(new Condition("$.origin","damian"));
		criteria.and(new Condition("$.amount","5"));
		criteria.and(new Condition("$.destination","owen"));

		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria, criteriaTwo));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "criteriaTwoResult", "criteriaResult");
	}

	@Test
	public void returnMatchStringManyMatchesManyCriteriaOneNoMatchAndReturnDefault2() {
		Criteria<String> criteria = new Criteria<String>("criteriaResult");
		criteria.and(new Condition("$.origin","damian"));
		criteria.and(new Condition("$.amount","5"));
		criteria.and(new Condition("$.destination","owen"));

		Criteria<String> criteriaTwo = new Criteria<String>("criteriaTwoResult");
		criteria.and(new Condition("$.origin","damian"));
		criteria.and(new Condition("$.amount","5"));
		criteria.and(new Condition("$.destination","owen"));

		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteriaTwo, criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "criteriaTwoResult", "criteriaResult");
	}

	private String getJson() {
		return "{'amount':5,'origin':'damian','destination':'owen'}";
	}

}
