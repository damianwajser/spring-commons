package com.github.damianwajser.tests;

import com.github.damianwajser.exceptions.impl.authentication.forbidden.ForbiddenException;
import com.github.damianwajser.exceptions.impl.authentication.forbidden.PermissionDeniedException;
import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.exceptions.impl.badrequest.ConflictException;
import com.github.damianwajser.exceptions.impl.notfound.NotFoundException;
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

		new ForbiddenException(getDetails());
		assertThat(new ForbiddenException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);

		new PermissionDeniedException(getDetails());
		assertThat(new PermissionDeniedException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.FORBIDDEN);

		new BadRequestException(getDetails());
		assertThat(new BadRequestException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		new ConflictException(getDetails());
		assertThat(new ConflictException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.CONFLICT);

		new NotFoundException(getDetails());
		assertThat(new NotFoundException(getDetail()).getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND);
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
