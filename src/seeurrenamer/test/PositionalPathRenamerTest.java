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
import seeurrenamer.main.util.PositionalPathRenamer;

public class PositionalPathRenamerTest {

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
		this.pathList.add(new SelectedPath(Paths.get("/xyz/jadi.jar")));
		this.pathList.add(new SelectedPath(Paths.get("/xyz/jada.jar")));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRenameWithLeftAndInsert() {
		PositionalPathRenamer pathRenamer = new PositionalPathRenamer(
				PositionalPathRenamer.INSERT_OPERATION,
				PositionalPathRenamer.LEFT_SIDE, 4);

		List<SelectedPath> expectedSelectedPaths = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadian.jar")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadaan.jar")));
		assertThat(pathRenamer.rename(pathList, "an"),
				is(equalTo(expectedSelectedPaths)));
	}

	@Test
	public void testRenameWithRightPositional() {
		PositionalPathRenamer pathRenamer = new PositionalPathRenamer(
				PositionalPathRenamer.INSERT_OPERATION,
				PositionalPathRenamer.RIGHT_SIDE, 3);

		List<SelectedPath> selectedPathList = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadi.anjar")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jada.anjar")));

		assertThat(pathRenamer.rename(pathList, "an"),
				is(equalTo(selectedPathList)));
	}

	@Test
	public void testRenameWithLeftPositionAndOverwrite() {
		PositionalPathRenamer pathRenamer = new PositionalPathRenamer(
				PositionalPathRenamer.OVERWRITE_OPERATION,
				PositionalPathRenamer.LEFT_SIDE, 4);

		List<SelectedPath> selectedPathList = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadianar")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadaanar")));

		assertThat(pathRenamer.rename(pathList, "an"),
				is(equalTo(selectedPathList)));

	}

	@Test
	public void testRenameWithRightPositionAndOverwrite() {
		PositionalPathRenamer pathRenamer = new PositionalPathRenamer(
				PositionalPathRenamer.OVERWRITE_OPERATION,
				PositionalPathRenamer.RIGHT_SIDE, 4);

		List<SelectedPath> selectedPathList = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadolear")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadolear")));

		assertThat(pathRenamer.rename(pathList, "ole"),
				is(equalTo(selectedPathList)));

	}
}
