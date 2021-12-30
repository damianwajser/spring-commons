package com.github.damianwajser.rest.configuration.okhttp;

import com.github.damianwajser.rest.configuration.CustomHttpRequestFactory;
import com.github.damianwajser.rest.configuration.properties.TimeoutConfigurationProperties;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(prefix = "spring.commons.rest.template", name = "implementation", havingValue = "OK_HTTP")
public class OkHttpRequestFactory implements CustomHttpRequestFactory {

	@Autowired
	private TimeoutConfigurationProperties timeouts;

	@Autowired
	private ConnectionPool okHttpConectionPool;

	@Autowired(required = false)
	private SSLContext sslContext;

	public ClientHttpRequestFactory getClientHttpRequestFactory(boolean configureSSl) {
		OkHttpClient.Builder builder = getHttpClientBuilder(configureSSl);
		builder.retryOnConnectionFailure(true);
		builder.connectionPool(okHttpConectionPool);
		return new OkHttp3ClientHttpRequestFactory(builder.build());
	}

	private OkHttpClient.Builder getHttpClientBuilder(boolean configureSSl) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (timeouts.getConnection() > 0) builder.connectTimeout(timeouts.getConnection(), TimeUnit.MILLISECONDS);
		if (timeouts.getRead() > 0) builder.readTimeout(timeouts.getRead(), TimeUnit.MILLISECONDS);
		if (timeouts.getWrite() > 0) builder.writeTimeout(timeouts.getWrite(), TimeUnit.MILLISECONDS);

		if (configureSSl == true && sslContext != null) {
			//TODO: resolver ssl
			builder.sslSocketFactory(sslContext.getSocketFactory(), null);
		}
		return builder;
	}

}
