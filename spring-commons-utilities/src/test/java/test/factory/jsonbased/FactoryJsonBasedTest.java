package test.factory.jsonbased;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.github.damianwajser.factories.jsonbased.conditions.Condition;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class FactoryJsonBasedTest {

	@Test
	public void returnDefaultNull() {
		FactoryCriteriaJsonBased<Object> factory = new FactoryCriteriaJsonBased<>(new Criteria());
		assertThat(factory.find(getJson()), empty());
	}

	@Test
	public void returnDefaultString() {
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(new Criteria("default", new ArrayList<>()));
		TestUtils.checkResult(factory.find(getJson()), "default");
	}

	@Test
	public void returnMatchStringOneMatch() {
		Criteria<String> criteria = new Criteria<>("default");
		criteria.addCriterion("damianResult")
				.and(new Condition("$.origin", "damian"));
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(criteria);
		TestUtils.checkResult(factory.find(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatches() {
		Criteria criteria = new Criteria("default");
		criteria.addCriterion("damianResult")
				.and(new Condition("$.origin", "damian"))
				.and(new Condition("$.amount", "5"))
				.and(new Condition("$.destination", "owen"));
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(criteria);
		TestUtils.checkResult(factory.find(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatchesOneNoMatchAndReturnDefault() {
		Criteria criteria = new Criteria("default");
		criteria.addCriterion("damianResult")
				.and(new Condition("$.origin", "damian"))
				.and(new Condition("$.amount", "5"))
				.and(new Condition("$.destination", "other"));
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(criteria);
		TestUtils.checkResult(factory.find(getJson()), "default");
	}

	@Test
	public void returnMatchStringManyMatchesManyCriteriaOneNoMatchAndReturnDefault() {
		Criteria criteria = new Criteria("default");

		criteria.addCriterion("criteriaResult")
				.and(new Condition("$.origin", "damian"))
				.and(new Condition("$.amount", "5"))
				.and(new Condition("$.destination", "owen"));

		criteria.addCriterion("criteriaTwoResult")
				.and(new Condition("$.origin", "damian"))
				.and(new Condition("$.amount", "5"))
				.and(new Condition("$.destination", "owen"));

		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(criteria);
		TestUtils.checkResult(factory.find(getJson()), "criteriaTwoResult", "criteriaResult");
	}

	private String getJson() {
		return "{'amount':5,'origin':'damian','destination':'owen'}";
	}

}
