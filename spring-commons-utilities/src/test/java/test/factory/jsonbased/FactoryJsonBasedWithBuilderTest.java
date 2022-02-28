package test.factory.jsonbased;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.criteria.Criterion;
import com.github.damianwajser.factories.jsonbased.criteria.builder.CriterionBuilder;
import org.junit.Test;

import java.util.Arrays;


public class FactoryJsonBasedWithBuilderTest {

	private void execute(String criterionStr, String result) {
		Criterion<String> criterion = CriterionBuilder.build(criterionStr, result);
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>(new Criteria("default", Arrays.asList(criterion)));
		TestUtils.checkResult(factory.find(getJson()), result);
	}

	@Test
	public void returnMatchStringOneMatch() {
		execute("$.origin=damian", "damianResult");
	}

	@Test
	public void returnMatchStringOneMatchWithSpaces() {
		execute("$.origin =damian", "damianResult");
	}

	@Test
	public void returnMatchStringOneMatchWithTwoSpaces() {
		execute("$.origin = damian", "damianResult");
	}

	@Test
	public void returnMatchStringManyMatches() {
		execute("$.origin = damian AND $.amount=5 AND $.destination=owen", "damianResult");
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
