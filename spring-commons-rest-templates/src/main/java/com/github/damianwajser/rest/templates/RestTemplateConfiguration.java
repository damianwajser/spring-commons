package com.github.damianwajser.rest.templates;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.github.damianwajser.rest.configuration.CustomClientHttpRequestFactory;
import com.github.damianwajser.rest.interceptors.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

	@Autowired
	private CustomClientHttpRequestFactory clientHttpRequestFactory;

	@Bean
	@Primary
	@Qualifier("default_template")
	public RestTemplate restTemplate() {
		return getRestTemplate(false);
	}

	@Bean
	@Qualifier("snake_template")
	public RestTemplate restTemplateSnake() {
		RestTemplate restTemplate = getSnakeRestTemplate(false);
		return restTemplate;
	}

	@Bean
	@Qualifier("ssl_camel_case_template")
	public RestTemplate restTemplateSsl() {
		return getRestTemplate(true);
	}

	@Bean
	@Qualifier("ssl_snake_case_template")
	public RestTemplate restTemplateSslSnake() {
		return getSnakeRestTemplate(true);
	}

	private RestTemplate getRestTemplate(boolean hasSslContext) {
		final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory.getClientHttpRequestFactory(hasSslContext));
		restTemplate.setInterceptors(getInterceptors());
		return restTemplate;
	}

	private List<ClientHttpRequestInterceptor> getInterceptors() {
		return Collections.singletonList(new RestTemplateInterceptor());
	}

	private RestTemplate getSnakeRestTemplate(boolean hasSSl) {
		RestTemplate restTemplate = getRestTemplate(hasSSl);
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();

		MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) restTemplate.getMessageConverters().stream()
				.filter(c -> MappingJackson2HttpMessageConverter.class.isAssignableFrom(c.getClass()))
				.findFirst()
				.orElseGet(() -> new MappingJackson2HttpMessageConverter());

		if (!converters.contains(converter)) {
			converters.add(converter);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.registerModule(new Jdk8Module());
		converter.setObjectMapper(mapper);
		return restTemplate;
	}
}
