package com.github.damianwajser.parsers;

import java.util.HashMap;
import java.util.Map;

public class Mapper {

	private Map<String, String> mappings;
	private boolean skipPathNotFound;

	public Mapper() {
		this.skipPathNotFound = true;
	}

	public Map<String, String> getMappings() {
		return mappings;
	}

	public void addMapping(String field, String template) {
		if (this.mappings == null) {
			this.mappings = new HashMap<>();
		}
		this.mappings.put(field, template);
	}

	public boolean isSkipPathNotFound() {
		return skipPathNotFound;
	}

	public void setSkipPathNotFound(boolean skipPathNotFound) {
		this.skipPathNotFound = skipPathNotFound;
	}
}
