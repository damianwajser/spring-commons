package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.impl.authentication.forbidden.ForbiddenException;
import com.github.damianwajser.exceptions.impl.authentication.forbidden.PermissionDeniedException;
import com.github.damianwajser.exceptions.impl.badrequest.*;
import com.github.damianwajser.exceptions.model.ExceptionDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class InstanciateObjectTest {

	@Test
	public void instanciateException() {
		//400
		assertThat(new BadRequestException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(new BadRequestException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(new BadRequestException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		//402
		assertThat(new PaymentRequiredException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
		assertThat(new PaymentRequiredException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
		assertThat(new PaymentRequiredException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);

		//403
		assertThat(new ForbiddenException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new ForbiddenException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new ForbiddenException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);

		//403
		assertThat(new PermissionDeniedException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new PermissionDeniedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);
		assertThat(new PermissionDeniedException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);

		//404
		assertThat(new NotFoundException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(new NotFoundException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(new NotFoundException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);

		//405
		assertThat(new MethodNotAllowedException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(new MethodNotAllowedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
		assertThat(new MethodNotAllowedException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);

		//406
		assertThat(new NotAcceptableException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(new NotAcceptableException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(new NotAcceptableException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);

		//409
		assertThat(new ConflictException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(new ConflictException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(new ConflictException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);

		//412
		assertThat(new PreconditionFailedException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
		assertThat(new PreconditionFailedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
		assertThat(new PreconditionFailedException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);

		//415
		assertThat(new UnsupportedMediaTypeException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		assertThat(new UnsupportedMediaTypeException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		assertThat(new UnsupportedMediaTypeException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

		//422
		assertThat(new UnprocessableEntityException(getDetails()).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(new UnprocessableEntityException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
		assertThat(new UnprocessableEntityException("code", "message", Optional.empty()).getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

	}

	private ExceptionDetail getDetail() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		return new ExceptionDetail("", "", Optional.empty());
	}

	private List<ExceptionDetail> getDetails() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		return Arrays.asList(new ExceptionDetail("", "", Optional.empty()));
	}
}
