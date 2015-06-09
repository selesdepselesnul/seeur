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
import seeurrenamer.main.util.PathRenamer;

public class PathRenamerTest {

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
		PathRenamer pathRenamer = new PathRenamer();

		List<SelectedPath> expectedSelectedPaths = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadian.jar")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadaan.jar")));
		assertThat(pathRenamer.rename(pathList, 4, "an", PathRenamer.LEFT_SIDE,
				PathRenamer.INSERT_OPERATION),
				is(equalTo(expectedSelectedPaths)));
	}

	@Test
	public void testRenameWithRightPositional() {
		PathRenamer pathRenamer = new PathRenamer();

		List<SelectedPath> selectedPathList = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadi.anjar")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jada.anjar")));

		assertThat(pathRenamer.rename(pathList, 3, "an",
				PathRenamer.RIGHT_SIDE, PathRenamer.INSERT_OPERATION),
				is(equalTo(selectedPathList)));
	}

	@Test
	public void testRenameWithLeftPositionAndOverwrite() {
		PathRenamer pathRenamer = new PathRenamer();

		List<SelectedPath> selectedPathList = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadianar")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadaanar")));

		assertThat(pathRenamer.rename(pathList, 4, "an", PathRenamer.LEFT_SIDE,
				PathRenamer.OVERWRITE_OPERATION), is(equalTo(selectedPathList)));

	}

	@Test
	public void testRenameWithRightPositionAndOverwrite() {
		PathRenamer pathRenamer = new PathRenamer();

		List<SelectedPath> selectedPathList = Arrays.asList(
				new SelectedPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadolear")),
				new SelectedPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadolear")));

		assertThat(pathRenamer.rename(pathList, 4, "ole",
				PathRenamer.RIGHT_SIDE, PathRenamer.OVERWRITE_OPERATION),
				is(equalTo(selectedPathList)));

	}
}
