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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class InstanciateObjectTest {

	@Test
	public void instanciateException() {
		new ForbiddenException(getDetails());
		new ForbiddenException(getDetail());

		new PermissionDeniedException(getDetails());
		new PermissionDeniedException(getDetail());

		new BadRequestException(getDetails());
		new BadRequestException(getDetail());

		new ConflictException(getDetails());
		new ConflictException(getDetail());

		new NotFoundException(getDetails());
		new NotFoundException(getDetail());
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
