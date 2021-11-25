package test;


import com.github.damianwajser.exceptions.RestException;
import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import com.github.damianwajser.optionals.wrappers.OptionalWrapper;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

public class OptionalTest {
	@Test
	public void CheckGetTest() throws Exception {
		String expected = "test";
		String result = OptionalWrapper.of(Optional.of(expected)).get();
		assertThat(result).isEqualTo(result);
	}

	@Test(expected = NullPointerException.class)
	public void NullTest() throws Exception {
		OptionalWrapper.of(null);
	}

	@Test
	public void unWrapTest() throws Exception {
		Optional<String> expected = Optional.of("test");
		Optional<String> result = OptionalWrapper.of(expected).unWrap();
		assertThat(result).isEqualTo(result);
	}

	@Test
	public void isPresentTrueTest() throws Exception {
		assertThat(OptionalWrapper.of(Optional.of("expected")).isPresent()).isTrue();
	}

	@Test
	public void isPresentTrueFalse() throws Exception {
		assertThat(OptionalWrapper.of(Optional.empty()).isPresent()).isFalse();
	}

	@Test(expected = BadRequestException.class)
	public void ifPresentWithException() throws Exception {
		OptionalWrapper.of(Optional.of("expected")).ifPresent(s -> {
			throw new BadRequestException("", "", Optional.empty());
		});
		failBecauseExceptionWasNotThrown(BadRequestException.class);
	}

	@Test
	public void ifPresentWithOutContent() throws RestException {
		OptionalWrapper.of(Optional.ofNullable(null)).ifPresent(s -> fail("no deberia pasar por aca", s));
		OptionalWrapper.of(Optional.empty()).ifPresent(s -> fail("no deberia pasar por aca", s));
	}

	@Test()
	public void ifPresentWithOutException() throws Exception {
		String expected = "test";
		AtomicReference<String> result = new AtomicReference<>();
		OptionalWrapper.of(Optional.of(expected)).ifPresent(s -> {
			result.set(s);
		});
		assertThat(result.get()).isEqualTo(expected);
	}

}
