package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.ExceptionFactory;
import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.impl.authentication.forbidden.ForbiddenException;
import com.github.damianwajser.exceptions.impl.badrequest.*;
import com.github.damianwajser.exceptions.impl.servererror.InternalServerErrorException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class InstanciateObjectTest {

	public void generiConstrunctorTest(HttpStatus status, Class<? extends RestException> clazz) {
		try {
			assertThat(ExceptionFactory.getException(getDetails(), status).getHttpCode()).isEqualTo(status);
			assertThat(ExceptionFactory.getException(getDetails(), status, new RuntimeException()).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(List.class).newInstance(getDetails()).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(ExceptionDetail.class).newInstance(getDetail()).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(ExceptionDetail.class, Exception.class).newInstance(getDetail(), new RuntimeException()).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(String.class, String.class, Optional.class).newInstance("code", "message", Optional.of("optional_message")).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(String.class, String.class, Optional.class, Exception.class).newInstance("code", "message", Optional.empty(), new RuntimeException()).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(String.class, String.class, Object[].class).newInstance("code", "message", new Object[]{"args1", "args2"}).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(String.class, Optional.class, String.class, Object[].class).newInstance("code", Optional.empty(), "message", new Object[]{"args1", "args2"}).getHttpCode()).isEqualTo(status);
			assertThat(clazz.getConstructor(String.class, Optional.class, Exception.class, String.class, Object[].class).newInstance("code", Optional.empty(), new RuntimeException(), "message", new Object[]{"args1", "args2"}).getHttpCode()).isEqualTo(status);
		} catch (Exception e) {
			fail("dont work", e);
		}
	}

	@Test
	public void instanciateExceptions() throws Exception {
		new HashMap<HttpStatus, Class<? extends RestException>>() {{
			put(HttpStatus.BAD_REQUEST, BadRequestException.class);
			put(HttpStatus.PAYMENT_REQUIRED, PaymentRequiredException.class);
			put(HttpStatus.FORBIDDEN, ForbiddenException.class);
			put(HttpStatus.NOT_FOUND, NotFoundException.class);
			put(HttpStatus.METHOD_NOT_ALLOWED, MethodNotAllowedException.class);
			put(HttpStatus.NOT_ACCEPTABLE, NotAcceptableException.class);
			put(HttpStatus.CONFLICT, ConflictException.class);
			put(HttpStatus.PRECONDITION_FAILED, PreconditionFailedException.class);
			put(HttpStatus.UNSUPPORTED_MEDIA_TYPE, UnsupportedMediaTypeException.class);
			put(HttpStatus.UNPROCESSABLE_ENTITY, UnprocessableEntityException.class);
			put(HttpStatus.LOCKED, LockedException.class);
			put(HttpStatus.INTERNAL_SERVER_ERROR, InternalServerErrorException.class);
		}}.forEach((k, v) -> generiConstrunctorTest(k, v));
	}

	private ExceptionDetail getDetail() {
		return new ExceptionDetail("", "", Optional.empty());
	}

	private List<ExceptionDetail> getDetails() {
		return Arrays.asList(getDetail());
	}
}
