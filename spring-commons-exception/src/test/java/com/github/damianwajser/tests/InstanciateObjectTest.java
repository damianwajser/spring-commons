package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.ExceptionFactory;
import com.github.damianwajser.exceptions.impl.authentication.forbidden.ForbiddenException;
import com.github.damianwajser.exceptions.impl.authentication.forbidden.PermissionDeniedException;
import com.github.damianwajser.exceptions.impl.badrequest.*;
import com.github.damianwajser.exceptions.impl.servererror.InternalServerErrorException;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class InstanciateObjectTest {

	@Test
	public void instanciateExceptionBadRequest() throws Exception {
		//400
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.BAD_REQUEST).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.BAD_REQUEST, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(new BadRequestException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(new BadRequestException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(new BadRequestException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void instanciateExceptionPaymentRequirement() throws Exception {
		//402
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.PAYMENT_REQUIRED).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.PAYMENT_REQUIRED, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
		assertThat(new PaymentRequiredException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
		assertThat(new PaymentRequiredException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
		assertThat(new PaymentRequiredException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
	}

	@Test
	public void instanciateExceptionPaymentForbidden() throws Exception {
		//403
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.FORBIDDEN).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.FORBIDDEN, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new ForbiddenException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new ForbiddenException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new ForbiddenException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		//403
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.FORBIDDEN).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.FORBIDDEN, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new PermissionDeniedException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new PermissionDeniedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new PermissionDeniedException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void instanciateExceptionNotFound() throws Exception {
		//404
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.NOT_FOUND).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.NOT_FOUND, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(new NotFoundException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(new NotFoundException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(new NotFoundException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void instanciateExceptionMethodNotAllowed() throws Exception {
		//405
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.METHOD_NOT_ALLOWED).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.METHOD_NOT_ALLOWED, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(new MethodNotAllowedException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(new MethodNotAllowedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(new MethodNotAllowedException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Test
	public void instanciateExceptionMethodNotAcceptable() throws Exception {
		//406
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.NOT_ACCEPTABLE).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.NOT_ACCEPTABLE, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(new NotAcceptableException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(new NotAcceptableException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(new NotAcceptableException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
	}

	@Test
	public void instanciateExceptionMethodConflict() throws Exception {
		//409
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.CONFLICT).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.CONFLICT, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(new ConflictException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(new ConflictException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(new ConflictException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);
	}

	@Test
	public void instanciateExceptionPreconditionFail() throws Exception {
		//412
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.PRECONDITION_FAILED).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.PRECONDITION_FAILED, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
		assertThat(new PreconditionFailedException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
		assertThat(new PreconditionFailedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
		assertThat(new PreconditionFailedException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
	}

	@Test
	public void instanciateExceptionUnssuportedMediaType() throws Exception {
		//415
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.UNSUPPORTED_MEDIA_TYPE).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		assertThat(new UnsupportedMediaTypeException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		assertThat(new UnsupportedMediaTypeException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		assertThat(new UnsupportedMediaTypeException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Test
	public void instanciateExceptionUnprocesableEntity() throws Exception {
		//422
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.UNPROCESSABLE_ENTITY).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.UNPROCESSABLE_ENTITY, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(new UnprocessableEntityException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(new UnprocessableEntityException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(new UnprocessableEntityException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test
	public void instanciateExceptionLocked() throws Exception {
		//423
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.LOCKED).getHttpCode()).isEqualTo(HttpStatus.LOCKED);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.LOCKED, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.LOCKED);
		assertThat(new LockedException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.LOCKED);
		assertThat(new LockedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.LOCKED);
		assertThat(new LockedException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.LOCKED);
	}

	@Test
	public void instanciateExceptionInternalError() throws Exception {
		//500
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.INTERNAL_SERVER_ERROR).getHttpCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(ExceptionFactory.getException(getDetails(), HttpStatus.INTERNAL_SERVER_ERROR, new RuntimeException()).getHttpCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(new InternalServerErrorException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(new InternalServerErrorException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(new InternalServerErrorException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ExceptionDetail getDetail() {
		return new ExceptionDetail("", "", Optional.empty());
	}

	private List<ExceptionDetail> getDetails() {
		return Arrays.asList(new ExceptionDetail("", "", Optional.empty()));
	}
}
