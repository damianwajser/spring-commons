package com.github.damianwajser.rest.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.commons.rest.template.ssl")
@PropertySource("classpath:spring-commons-ssl.properties")
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

	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}

	public void setTrustStorePassword(char[] trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
