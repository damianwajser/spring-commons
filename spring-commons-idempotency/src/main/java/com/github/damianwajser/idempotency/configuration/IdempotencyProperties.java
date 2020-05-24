package com.github.damianwajser.idempotency.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.commons.idempotency")
public class IdempotencyProperties {

	@Value("${spring.commons.idempotency.badrequest.code:400}")
	private String keyNotFoud;

	@Value("${spring.commons.idempotency.conflict.code:409}")
	private String conflictCode;

	@Value("${spring.commons.idempotency.conflict.mesasge:idempotency key is bussy}")
	private String conflictMessage;

	//Time in millis
	@Value("${spring.commons.idempotency.ttl:86400000}")
	private long ttl;

	public long getIdempotencyTtl() {
		return ttl;
	}

	public String getBadRequestCode() {
		return keyNotFoud;
	}

	public String getConflictCode() {
		return conflictCode;
	}

	public String getConflictMessage() {
		return conflictMessage;
	}

}
