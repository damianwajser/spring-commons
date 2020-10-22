package com.github.damianwajser.rest.configuration;

import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
@ConditionalOnProperty(name = "spring.commons.rest.template.ssl.enable", havingValue = "true")
public class SslDefaultBeanConfiguration {

	@Autowired
	private SslConfigurationProperties properties;

	@Bean
	public SSLContext getSSlContext() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		return SSLContextBuilder.create()
				.loadTrustMaterial(new URL(properties.getTrustStore()),
						properties.getTrustStorePassword())
				.setProtocol(properties.getProtocol())
				.build();
	}
}
