package com.github.damianwajser.rest.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource("classpath:spring-commons-timeouts.properties")
public class PropertiesRestTemplate {
}
