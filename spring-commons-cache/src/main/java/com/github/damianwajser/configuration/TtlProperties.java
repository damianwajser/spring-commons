package com.github.damianwajser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring-commons.cache.ttl")
@Configuration
public class TtlProperties {


	private Map<String, Integer> name = new HashMap<>();

	private int all = 86400;

	public void setName(Map<String, Integer> name) {
		this.name = name;
	}

	public int getGenericTTl() {
		return this.all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public Map<String, Integer> getTtls() {
		return this.name;
	}
}
