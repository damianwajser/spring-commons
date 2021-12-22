package com.github.damianwajser.model.global.email;

import com.github.damianwajser.validator.annotation.global.Email;
import org.springframework.http.HttpMethod;

public class EmailObject {

	@Email(businessCode = "a-400", excludes = HttpMethod.PATCH, message = "")
	private Object value;

	public EmailObject() {
	}

	public EmailObject(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
