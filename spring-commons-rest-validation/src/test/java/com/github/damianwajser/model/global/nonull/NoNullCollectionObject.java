package com.github.damianwajser.model.global.nonull;

import com.github.damianwajser.validator.annotation.global.NotNull;

import java.util.Collection;

public class NoNullCollectionObject {

	@NotNull(businessCode = "a-400")
	private Collection<?> value;

	public NoNullCollectionObject() {
	}

	public NoNullCollectionObject(Collection<?> value) {
		super();
		this.value = value;
	}

	public Collection<?> getValue() {
		return value;
	}

	public void setValue(Collection<?> value) {
		this.value = value;
	}
}
