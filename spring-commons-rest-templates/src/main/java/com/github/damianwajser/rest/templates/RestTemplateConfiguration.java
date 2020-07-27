package com.github.damianwajser.rest.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.damianwajser.rest.interceptors.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

	@Bean
	@Primary
	@Qualifier("default_template")
	public RestTemplate restTemplate() {
		return getRestTemplate();
	}

	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setInterceptors(getInterceptors());
		return restTemplate;
	}

	private List<ClientHttpRequestInterceptor> getInterceptors() {
		return Collections.singletonList(new RestTemplateInterceptor());
	}

	@Bean
	@Qualifier("snake_template")
	public RestTemplate restTemplateSnake() {
		RestTemplate restTemplate = getRestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();
		jsonMessageConverter.setObjectMapper(mapper);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		messageConverters.add(jsonMessageConverter);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
}
