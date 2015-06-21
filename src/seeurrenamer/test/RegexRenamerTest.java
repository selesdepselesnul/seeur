package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.RegexRenamer;

public class RegexRenamerTest {

	private List<PairPath> pathList;
	private PathsRenamer pathRenamer;

	@Before
	public void setUp() throws Exception {
		this.pathRenamer = new PathsRenamer();
		this.pathList = new ArrayList<>();
		this.pathList.add(new PairPath(Paths.get("/xyz/jadi_mysql_.jar")));

	}

	@Test
	public void testRenameWithCaseSensitive() {
		RegexRenamer regexPathRenamer = new RegexRenamer("mysql", "sos",
				false);
		List<PairPath> expectedList = Arrays.asList(new PairPath(Paths
				.get("/xyz/jadi_mysql_.jar"), Paths.get("/xyz/jadi_sos_.jar")));
		assertThat(pathRenamer.rename(this.pathList, regexPathRenamer),
				is(equalTo(expectedList)));
	}

	@Test
	public void testRenameWithCaseInsensitive() {
		RegexRenamer regexPathRenamer = new RegexRenamer("MYSQL", "sos", true);
		List<PairPath> expectedList = Arrays.asList(new PairPath(Paths
				.get("/xyz/jadi_mysql_.jar"), Paths.get("/xyz/jadi_sos_.jar")));
		assertThat(pathRenamer.rename(this.pathList, regexPathRenamer),
				is(equalTo(expectedList)));
	}

}
