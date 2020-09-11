package com.github.damianwajser.model.global.max;

import com.github.damianwajser.validator.annotation.global.Max;

public class MaxStringObject {

	@Max(max = 2, businessCode = "a-400")
	private String value;
	@Max(max = 2, businessCode = "a-400", isNulleable = true)
	private String nulleable;
	@Max(value = 2, businessCode = "a-400")
	private String aliases;
	@Max(value = 2, max = 5, businessCode = "a-400")
	private String invalidAliases;

	public MaxStringObject() {
	}

	public MaxStringObject(String value) {
		super();
		this.value = value;
	}
	
	public MaxStringObject(String value, String aliases, String invalidAliases) {
		super();
		this.value = value;
		this.aliases = aliases;
		this.invalidAliases = invalidAliases;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getAliases() {
		return aliases;
	}
	
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
}
