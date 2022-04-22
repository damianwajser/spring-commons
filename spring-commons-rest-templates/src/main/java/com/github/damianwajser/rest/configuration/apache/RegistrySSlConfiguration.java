package com.github.damianwajser.rest.configuration.apache;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class RegistrySSlConfiguration {

    @Bean
    public Registry<ConnectionSocketFactory> registry(SSLContext sslContext){
        final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                new String[] { "TLSv1.2" },
                new String[] { "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384" },
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();
        return registry;
    }
}
