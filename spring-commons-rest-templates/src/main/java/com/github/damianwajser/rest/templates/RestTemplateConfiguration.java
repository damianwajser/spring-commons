package com.github.damianwajser.rest.templates;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.github.damianwajser.rest.configuration.TimeoutConfigurationProperties;
import com.github.damianwajser.rest.interceptors.RestTemplateInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

	@Value("${spring.commons.rest.template.date.serializer.format.local.date}")
	private String formatLocalDateSerializer;

	@Value("${spring.commons.rest.template.date.deserializer.format.local.date}")
	private String formatLocalDateDeserializer;

	@Autowired(required = false)
	private SSLContext sslContext;

	@Autowired
	private TimeoutConfigurationProperties timeouts;

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
		final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory(hasSslContext));
		restTemplate.setInterceptors(getInterceptors());

		return restTemplate;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory(boolean hasSslContext) {
		final RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeouts.getConnection())
				.setConnectionRequestTimeout(timeouts.getWrite())
				.setSocketTimeout(timeouts.getRead())
				.build();
		HttpClientBuilder builder = HttpClientBuilder
				.create()
				.setDefaultRequestConfig(config);
		if (hasSslContext == true && sslContext != null) {
			builder.setSSLContext(sslContext);
		}
		return new HttpComponentsClientHttpRequestFactory(builder.build());
	}

	private List<ClientHttpRequestInterceptor> getInterceptors() {
		return Collections.singletonList(new RestTemplateInterceptor());
	}

	@NotNull
	private RestTemplate getSnakeRestTemplate(boolean hasSSl) {
		RestTemplate restTemplate = getRestTemplate(hasSSl);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addDeserializer(
				LocalDate.class,
				new LocalDateDeserializer(DateTimeFormatter.ofPattern(formatLocalDateSerializer)));
		javaTimeModule.addSerializer(
				LocalDate.class,
				new LocalDateSerializer(DateTimeFormatter.ofPattern(formatLocalDateDeserializer)));
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(javaTimeModule);
		jsonMessageConverter.setObjectMapper(mapper);
		messageConverters.add(jsonMessageConverter);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
}
