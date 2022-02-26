package test.usescases.notifications.configurations;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.github.damianwajser.factories.jsonbased.criteria.Criteria;
import com.github.damianwajser.factories.jsonbased.criteria.builder.CriteriaBuilder;
import com.github.damianwajser.parsers.JsonParser;
import com.github.damianwajser.parsers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class FactoryConfiguration {

	@Autowired
	private NotificationProperties properties;

	private static Criteria<NotificationConfiguration> apply(NotificationConfiguration n) {
		Mapper mapper = new Mapper();
		n.getMapping().forEach((k, v) -> mapper.addMapping(k, v));
		n.setParser(new JsonParser(mapper));
		return CriteriaBuilder.build(n.getCondition(), n);
	}

	@Bean
	public FactoryCriteriaJsonBased<NotificationConfiguration> factoryCriteriaJsonBased() {
		Collection<Criteria> criterias = properties.getNotifications().values().stream().map(FactoryConfiguration::apply).collect(Collectors.toList());
		return new FactoryCriteriaJsonBased(criterias);
	}

}
