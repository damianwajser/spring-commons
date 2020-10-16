package com.github.damianwajser.rest.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.commons.rest.template.ssl")
public class SslConfigurationProperties {
	/**
	 * URL location, typically with file:// scheme, of a CA trust store file in JKS format.
	 */
	String trustStore;

	/**
	 * The store password of the given trust store.
	 */
	char[] trustStorePassword;

	/**
	 * One of the SSLContext algorithms listed at
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SSLContext .
	 */
	String protocol;

	public String getTrustStore() {
		return trustStore;
	}

	public char[] getTrustStorePassword() {
		return trustStorePassword;
	}

	public String getProtocol() {
		return protocol;
	}
}
