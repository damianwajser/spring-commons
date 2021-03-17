package com.github.damianwajser.rest.configuration;

import org.springframework.http.client.ClientHttpRequestFactory;

public interface CustomHttpRequestFactory {

	ClientHttpRequestFactory getClientHttpRequestFactory(boolean configureSSl);

}
