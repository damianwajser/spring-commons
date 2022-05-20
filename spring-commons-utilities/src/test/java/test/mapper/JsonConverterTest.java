package test.mapper;

import com.github.damianwajser.parsers.JsonToObjectConverter;
import com.github.damianwajser.parsers.Mapper;
import org.junit.Test;
import test.mapper.model.NotificationMapperModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class JsonConverterTest {

	@Test
	public void checkRegexTwoRepetition() throws Exception {
		String json = getJson();
		Mapper mapper = new Mapper();
		mapper.addMapping("title", "Congratulations! has received $$.amount discount, yes $$.amount discount");
		JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
		NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
		assertThat(result.getTitle()).isEqualTo("Congratulations! has received $5 discount, yes $5 discount");
	}

	@Test
	public void checkEmptySubstitution() throws Exception {
		String json = getJson();
		Mapper mapper = new Mapper();
		mapper.addMapping("title", "Congratulations! has received $5 discount, yes $5 discount");
		JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
		NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
		assertThat(result.getTitle()).isEqualTo("Congratulations! has received $5 discount, yes $5 discount");
	}

	@Test
	public void completeMapper() throws Exception {
		String json = getJson();
		Mapper mapper = new Mapper();
		mapper.addMapping("body", "$.destination, $.origin sent you $$.amount dollars");
		mapper.addMapping("title", "Congrats! $.destination sent you $$.amount dollars");
		mapper.addMapping("footer", "Regards!");
		JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
		NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
		assertThat(result.getBody()).isEqualTo("owen, damian sent you $5 dollars");
		assertThat(result.getTitle()).isEqualTo("Congrats! owen sent you $5 dollars");
		assertThat(result.getFooter()).isEqualTo("Regards!");
	}

	@Test
	public void completeMapperNumeric() throws Exception {
		String json = getJsonNumeric();
		Mapper mapper = new Mapper();
		mapper.addMapping("body", "$.destination, $.origin sent you $$.amount dollars");
		mapper.addMapping("title", "Congrats! $.destination sent you $$.amount dollars");
		mapper.addMapping("footer", "Regards!");
		JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
		NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
		assertThat(result.getBody()).isEqualTo("owen, damian sent you $5 dollars");
		assertThat(result.getTitle()).isEqualTo("Congrats! owen sent you $5 dollars");
		assertThat(result.getFooter()).isEqualTo("Regards!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void errorFieldNotExist() throws Exception {
		String json = getJson();
		Mapper mapper = new Mapper();
		mapper.addMapping("body_dont_exist", "$.destination, $.origin sent you $$.amount dollars");
		JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
		NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
		fail("dont work");
	}

	@Test
	public void errorPathNotExistWithSkip() throws Exception {
		String json = getJson();
		Mapper mapper = new Mapper();
		mapper.addMapping("title", "Congratulations $.notFound");
		JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
		NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
		assertThat(result.getTitle()).isEqualTo("Congratulations ");
	}

	@Test(expected = com.jayway.jsonpath.PathNotFoundException.class)
	public void errorPathNotExistWithNotSkip() throws Exception {
		String json = getJson();
		Mapper mapper = new Mapper();
		mapper.addMapping("title", "Congratulations $.notFound");
		JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
		mapper.setSkipPathNotFound(false);
		NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
		fail("don't work");
	}

	private String getJson() {
		return "{'amount':'5','origin':'damian','destination':'owen'}";
	}
	private String getJsonNumeric() {
		return "{'amount':5,'origin':'damian','destination':'owen'}";
	}

}
