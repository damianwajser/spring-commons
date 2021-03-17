package com.github.damianwajser.model;

import com.github.damianwajser.validator.annotation.global.NotEmpty;

public class EnumModel {

	@NotEmpty(businessCode = "asd")
	private TEST a;


	public TEST getA() {
		return a;
	}

	public void setA(TEST a) {
		this.a = a;
	}

	public enum TEST {
		A, B;
	}
}
