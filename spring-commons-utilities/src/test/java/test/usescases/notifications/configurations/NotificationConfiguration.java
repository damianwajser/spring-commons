package test.usescases.notifications.configurations;

import com.github.damianwajser.parsers.JsonParser;
import test.usescases.notifications.model.SenderModel;

import java.util.Map;

public class NotificationConfiguration {

	private String condition;
	private SenderModel sender;
	private Map<String,String> mapping;
	private JsonParser parser;

	public JsonParser getParser() {
		return parser;
	}

	public void setParser(JsonParser parser) {
		this.parser = parser;
	}

	public Map<String,String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String,String> mapping) {
		this.mapping = mapping;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public SenderModel getSender() {
		return sender;
	}

	public void setSender(SenderModel sender) {
		this.sender = sender;
	}
}
