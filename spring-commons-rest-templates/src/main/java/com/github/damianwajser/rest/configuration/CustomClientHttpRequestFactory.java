package com.github.damianwajser.rest.configuration;

import com.github.damianwajser.rest.configuration.properties.TimeoutConfigurationProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

@Component
public class CustomClientHttpRequestFactory {

	@Autowired
	private TimeoutConfigurationProperties timeouts;

	@Autowired
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

	@Autowired(required = false)
	private SSLContext sslContext;

	public ClientHttpRequestFactory getClientHttpRequestFactory(boolean configureSSl) {
		final RequestConfig config = getConfig();
		HttpClientBuilder builder = getHttpClientBuilder(configureSSl, config);
		return new HttpComponentsClientHttpRequestFactory(builder.build());
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
