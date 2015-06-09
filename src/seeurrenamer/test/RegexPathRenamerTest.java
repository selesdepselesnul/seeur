package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.RegexPathRenamer;

public class RegexPathRenamerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private List<SelectedPath> pathList;

	@Before
	public void setUp() throws Exception {
		this.pathList = new ArrayList<>();
		this.pathList.add(new SelectedPath(Paths.get("/xyz/jadi_mysql_.jar")));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRenameWithCaseSensitive() {
		RegexPathRenamer regexPathRenamer = new RegexPathRenamer("mysql");
		List<SelectedPath> expectedList = Arrays.asList(new SelectedPath(Paths.get("/xyz/jadi_mysql_.jar"), Paths
				.get("/xyz/jadi_sos_.jar")));
		assertThat(regexPathRenamer.rename(this.pathList, "sos"), is(equalTo(expectedList)));
	}
}
