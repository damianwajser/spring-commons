package com.github.damianwajser.optionals.wrappers;

import com.github.damianwajser.exceptions.RestException;

import java.util.Objects;
import java.util.Optional;

public class OptionalWrapper<T> {

	private final Optional<T> value;

	private OptionalWrapper(Optional<T> value) {
		this.value = Objects.requireNonNull(value);
	}

	public void ifPresent(ConsumerWrapper<T> consumer) throws RestException {
		if (value.isPresent())
			consumer.acceptWithException(value.get());
	}

	public static <T> OptionalWrapper<T> of(Optional<T> value) {
		return new OptionalWrapper<T>(value);
	}

	public T get() {
		return this.value.get();
	}

	public Optional<T> unWrap() {
		return this.value;
	}

	public boolean isPresent() {
		return this.value.isPresent();
	}
}
