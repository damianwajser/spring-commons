package com.github.damianwajser.model.global.size;

import com.github.damianwajser.validator.annotation.global.Size;

import java.util.Collection;

public class SizeCollectionObject {

	@Size(max = 5, businessCode = "c-400")
	private Collection<Integer> value;

	public SizeCollectionObject() {
	}

	public SizeCollectionObject(Collection<Integer> value) {
		super();
		this.value = value;
	}

	public Collection<Integer> getValue() {
		return value;
	}

	public void setValue(Collection<Integer> value) {
		this.value = value;
	}
}
