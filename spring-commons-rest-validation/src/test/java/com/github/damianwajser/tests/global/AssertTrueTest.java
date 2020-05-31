package com.github.damianwajser.tests.global;

import com.github.damianwajser.model.global.asserttrue.AssertTrueObject;
import com.github.damianwajser.model.global.asserttrue.AssertTrueStringObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.damianwajser.model.TestUtils.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class AssertTrueTest {

	@Test
	public void true_Boolean() throws Exception {
		assertThat(validationFor(new AssertTrueObject(), onField("value")), fails());
		assertThat(validationFor(new AssertTrueObject(false), onField("value")), fails());
		assertThat(validationFor(new AssertTrueObject(true), onField("value")), succedes());
	}

	@Test
	public void true_object() throws Exception {
		assertThat(validationFor(new AssertTrueStringObject(), onField("value")), fails());
		assertThat(validationFor(new AssertTrueStringObject("false"), onField("value")), fails());
		assertThat(validationFor(new AssertTrueStringObject("true"), onField("value")), fails());
	}

}
