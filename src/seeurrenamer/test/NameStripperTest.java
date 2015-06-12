package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.function.Function;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NameStripperTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStripSuffix() {
		Function<String, String> suffixStripper = (string) -> string
				.replaceAll("\\.\\w+$", "");
		String newString = suffixStripper.apply("test ghjfsf.hmtl");
		assertThat(newString, is(equalTo("test ghjfsf")));
	}

	@Test
	public void testStripName() {
		Function<String, String> nameStripper = (string) -> string.replaceAll(
				"^.*\\.", "");
		String newString = nameStripper.apply("test. ghjf.sf.html");
		assertThat(newString, is(equalTo("html")));
	}
}
