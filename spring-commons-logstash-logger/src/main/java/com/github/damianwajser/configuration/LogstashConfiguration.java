package com.github.damianwajser.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damianwajser.filter.MDCFilter;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "logstash")
public class LogstashConfiguration {

	@Value("${logstash.destination:localhost:5000}")
	private String destination;

	@Value("${logstash.appName:test}")
	private String appName;

	@Value("${logstash.maxPayload:16000}")
	private Integer maxPayLoad;

	private Map<String, String> customFields;

	public Map<String, String> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, String> customFields) {
		this.customFields = customFields;
	}

	@PostConstruct
	public void init() {
		ObjectMapper mapper = new ObjectMapper();
		Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		LoggerContext loggerContext = rootLogger.getLoggerContext();
		Logger log = loggerContext.getLogger(LogstashConfiguration.class);
		log.info("Configurate  Logger, destination: " + destination);
		if (destination != null && !destination.equals("")) {;
			// loggerContext.reset(); // shouldn't need to use that

			LogstashTcpSocketAppender socketAppender = new LogstashTcpSocketAppender();
			socketAppender.setName("logstash");
			socketAppender.setContext(loggerContext);
			socketAppender.addDestination(destination);

			LogstashEncoder encoder = new LogstashEncoder();
			encoder.setContext(loggerContext);
			try {
				addcustomField("app_name", appName);
				encoder.setCustomFields(mapper.writeValueAsString(customFields));
			} catch (JsonProcessingException e) {
				encoder.setCustomFields("{ \"app_name\": \"" + appName + "\" }");
			}
			encoder.start();

			socketAppender.setEncoder(encoder);
			socketAppender.start();

			AsyncAppender asyncAppender = new AsyncAppender();
			asyncAppender.addAppender(socketAppender);
			asyncAppender.start();

			rootLogger.addAppender(asyncAppender);
		}
	}

	private void addcustomField(String string, String value) {
		if (this.getCustomFields() == null) {
			this.setCustomFields(new HashMap<>());
		}
		this.getCustomFields().put(string, value);

	}

	@Bean
	public Filter logFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(maxPayLoad);

		return filter;
	}

	@Bean
	public Filter mdcFilter() {
		return new MDCFilter() {
			@Override
			public Map<String, String> getProperties() {
				return new HashMap<>();
			}
		};
	}

}
