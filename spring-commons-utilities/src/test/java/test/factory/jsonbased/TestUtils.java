package test.factory.jsonbased;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

public class TestUtils {

	public static void checkResult(Collection<String> actual, String... expected) {
		assertThat(actual, hasItems(expected));
		assertThat(actual, hasSize(expected.length));
	}
}
