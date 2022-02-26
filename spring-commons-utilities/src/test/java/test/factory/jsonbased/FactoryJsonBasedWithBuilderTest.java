package test.factory.jsonbased;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.criteria.builder.CriteriaBuilder;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


public class FactoryJsonBasedWithBuilderTest {

	@Test
	public void returnMatchStringOneMatch() {
		Criteria<String> criteria = CriteriaBuilder.build("$.origin=damian","damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "damianResult");
	}
	@Test
	public void returnMatchStringOneMatchWithSpaces() {
		Criteria<String> criteria = CriteriaBuilder.build("$.origin =damian","damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringOneMatchWithTwoSpaces() {
		Criteria<String> criteria = CriteriaBuilder.build("$.origin = damian","damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatches() {
		Criteria<String> criteria = CriteriaBuilder.build("$.origin = damian AND $.amount=5 AND $.destination=owen","damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "damianResult");
	}

	@Test
	public void returnMatchStringManyMatchesOneNoMatchAndReturnDefault() {
		Criteria<String> criteria = CriteriaBuilder.build("$.origin = damian AND $.amount=5 AND $.destination=other","damianResult");
		FactoryCriteriaJsonBased<String> factory = new FactoryCriteriaJsonBased<>("default", Arrays.asList(criteria));
		TestUtils.checkResult(factory.applyCriteria(getJson()), "default");
	}

	private String getJson() {
		return "{'amount':5,'origin':'damian','destination':'owen'}";
	}

}
