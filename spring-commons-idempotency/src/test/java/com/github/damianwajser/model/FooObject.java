package com.github.damianwajser.model;

import java.util.Objects;

public class FooObject {
	private String value;

	public FooObject(String value) {
		this.value = value;
	}

	public FooObject(){ }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FooObject)) return false;
		FooObject fooObject = (FooObject) o;
		return Objects.equals(getValue(), fooObject.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getValue());
	}
}
