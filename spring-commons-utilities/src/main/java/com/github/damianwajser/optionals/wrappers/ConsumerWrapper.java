package com.github.damianwajser.optionals.wrappers;

import com.github.damianwajser.exceptions.RestException;

@FunctionalInterface
public interface ConsumerWrapper<T> {

	void acceptWithException(T t) throws RestException;
}
