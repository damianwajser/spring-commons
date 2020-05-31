package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.nonull.NoNullCollectionObject;
import com.github.damianwajser.model.global.nonull.NoNullStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class NotNullTest {

	@Test
	public void not_null_string() throws Exception {
		assertThat(validationFor(new NoNullStringObject(), onField("value")), fails());
		assertThat(validationFor(new NoNullStringObject("4"), onField("value")), succedes());
	}

	@Test
	public void noNull_collection() throws Exception {
		assertThat(validationFor(new NoNullCollectionObject()), fails());
		assertThat(validationFor(new NoNullCollectionObject(Arrays.asList(null, null))), succedes());
		assertThat(validationFor(new NoNullCollectionObject(Arrays.asList(1, 2, 3, 4, 5, 6))), succedes());
	}

}
