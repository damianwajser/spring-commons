package com.github.damianwajser.configuration;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;

@Configuration
@ConditionalOnProperty(name = "spring.commons.logstash.enabled", havingValue = "true")
public class LogstashConfiguration {

	@Value("${spring.commons.logstash.destination:localhost:5000}")
	private String destination;

	@Autowired
	private PropertiesLogger propertiesLogger;

	@PostConstruct
	public void init() {
		ObjectMapper mapper = new ObjectMapper();
		Logger rootLogger = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
		LoggerContext loggerContext = rootLogger.getLoggerContext();
		Logger log = loggerContext.getLogger(LogstashConfiguration.class);
		log.info("Configurate  Logger, destination: {}", destination);
		if (destination != null && !destination.equals("")) {
			// loggerContext.reset(); // shouldn't need to use that
			LogstashTcpSocketAppender socketAppender = new LogstashTcpSocketAppender();
			socketAppender.setName("logstash");
			socketAppender.setContext(loggerContext);
			socketAppender.addDestination(destination);

			LogstashEncoder encoder = new LogstashEncoder();
			encoder.setContext(loggerContext);
			try {
				encoder.setCustomFields(mapper.writeValueAsString(propertiesLogger.getPropetiesToShow()));
			} catch (JsonProcessingException e) {
				encoder.setCustomFields("{ " + PropertiesLogger.APP_NAME + ": \"" + propertiesLogger.getAppName() + "\" }");
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


}
