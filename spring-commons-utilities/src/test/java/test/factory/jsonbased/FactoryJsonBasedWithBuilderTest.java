package test.factory.jsonbased;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.criteria.Criterion;
import com.github.damianwajser.factories.jsonbased.criteria.builder.CriterionBuilder;
import org.junit.Test;

import java.util.Arrays;


public class FactoryJsonBasedWithBuilderTest {

	@Test
	public void returnMatchStringOneMatch() {
		Criterion<String> criterion = CriterionBuilder.build("$.origin=damian", "damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(new Criteria("default", Arrays.asList(criterion)));
		TestUtils.checkResult(factory.find(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringOneMatchWithSpaces() {
		Criterion<String> criterion = CriterionBuilder.build("$.origin =damian", "damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(new Criteria("default", Arrays.asList(criterion)));
		TestUtils.checkResult(factory.find(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringOneMatchWithTwoSpaces() {
		Criterion<String> criterion = CriterionBuilder.build("$.origin = damian", "damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(new Criteria("default", Arrays.asList(criterion)));
		TestUtils.checkResult(factory.find(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatches() {
		Criterion<String> criterion = CriterionBuilder.build("$.origin = damian AND $.amount=5 AND $.destination=owen", "damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(new Criteria("default", Arrays.asList(criterion)));
		TestUtils.checkResult(factory.find(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatchesOneNoMatchAndReturnDefault() {
		Criterion<String> criterion = CriterionBuilder.build("$.origin = damian AND $.amount=5 AND $.destination=other", "damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(new Criteria("default", Arrays.asList(criterion)));
		TestUtils.checkResult(factory.find(getJson()), "default");
	}

	private String getJson() {
		return "{'amount':5,'origin':'damian','destination':'owen'}";
	}

}
