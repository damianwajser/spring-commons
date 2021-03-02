package com.github.damianwajser.rest.configuration.apache;

import com.github.damianwajser.rest.configuration.CustomHttpRequestFactory;
import com.github.damianwajser.rest.configuration.properties.TimeoutConfigurationProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.net.SocketException;

@Component
@ConditionalOnProperty(name = "spring.commons.rest.template.implementation", havingValue = "HTTP_CLIENT", matchIfMissing = true)
public class ApacheClientHttpRequestFactory implements CustomHttpRequestFactory {

	@Autowired
	private TimeoutConfigurationProperties timeouts;

	@Autowired
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

	@Autowired(required = false)
	private SSLContext sslContext;

	public ClientHttpRequestFactory getClientHttpRequestFactory(boolean configureSSl) {
		final RequestConfig config = getConfig();
		HttpClientBuilder builder = getHttpClientBuilder(configureSSl, config);
		configureRetry(builder);
		return new HttpComponentsClientHttpRequestFactory(builder.build());
	}

	private void configureRetry(HttpClientBuilder builder) {
		builder.setRetryHandler((exception, executionCount, context) -> {
			if (executionCount > 3) {
				return false;
			}
			return exception instanceof org.apache.http.NoHttpResponseException || exception instanceof SocketException;
		}).build();
	}

	private HttpClientBuilder getHttpClientBuilder(boolean configureSSl, RequestConfig config) {
		HttpClientBuilder builder = HttpClientBuilder
				.create()
				.setConnectionManager(poolingHttpClientConnectionManager)
				.setDefaultRequestConfig(config);
		if (configureSSl == true && sslContext != null) {
			builder.setSSLContext(sslContext);
		}
		return builder;
	}

	private RequestConfig getConfig() {
		final RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeouts.getConnection())
				.setConnectionRequestTimeout(timeouts.getWrite())
				.setSocketTimeout(timeouts.getRead())
				.build();
		return config;
	}

}
