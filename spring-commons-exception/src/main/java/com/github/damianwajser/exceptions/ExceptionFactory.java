package com.github.damianwajser.exceptions;

import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.reflections.Reflections;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExceptionFactory {

	private static Map<HttpStatus, Class<RestException>> exceptionCache = new ConcurrentHashMap<>();

	private ExceptionFactory() {
	}

	public static RestException getException(List<ExceptionDetail> details, HttpStatus status) throws
			ReflectiveOperationException {
		return getException(details, status, null);
	}

	public static RestException getException(List<ExceptionDetail> details, HttpStatus status, Exception e) throws
			ReflectiveOperationException {
		Class<RestException> exception = exceptionCache.computeIfAbsent(status, k ->
				new Reflections(RestException.class.getPackage().getName()).getTypesAnnotatedWith(ResponseStatus.class)
						.stream()
						.filter(c -> c.getAnnotation(ResponseStatus.class).code().equals(status))
						.map(c -> (Class<RestException>) c)
						.findFirst()
						.orElse(RestException.class));
		exceptionCache.put(status, exception);
		return exception.getDeclaredConstructor(List.class, Exception.class).newInstance(details, e);
	}
}
