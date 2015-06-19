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
import seeurrenamer.main.util.positionalrenaming.InsertionPositionalRenamer;
import seeurrenamer.main.util.positionalrenaming.OverwritingPositionalRenamer;
import seeurrenamer.main.util.positionalrenaming.PositionalRenaming;
import seeurrenamer.main.util.positionalrenaming.PositionalRenamingDirection;

public class PositionalRenamingTest {

	private PathsRenamer pathRenamer;
	private List<PairPath> pathList;

	@Before
	public void setUp() throws Exception {
		this.pathList = new ArrayList<>();
		this.pathRenamer = new PathsRenamer();
		this.pathList.add(new PairPath(Paths.get("/xyz/jadi.jar")));
		this.pathList.add(new PairPath(Paths.get("/xyz/jada.jar")));
	}

	@Test
	public void testRenameWithInsertionAndLeftDirection() {
		PositionalRenaming positionalPathRenamer = new InsertionPositionalRenamer(
				PositionalRenamingDirection.LEFT, 4, "an");
		List<PairPath> expectedSelectedPaths = Arrays.asList(
				new PairPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadian.jar")),
				new PairPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadaan.jar")));
		assertThat(this.pathRenamer.rename(pathList, positionalPathRenamer),
				is(equalTo(expectedSelectedPaths)));
	}

	@Test
	public void testRenameWithInsertionAndRightDirection() {
		PositionalRenaming positionalPathRenamer = new InsertionPositionalRenamer(
				PositionalRenamingDirection.RIGHT, 3, "an");

		List<PairPath> expectedSelectedPaths = Arrays.asList(
				new PairPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadi.anjar")),
				new PairPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jada.anjar")));
		assertThat(this.pathRenamer.rename(pathList, positionalPathRenamer),
				is(equalTo(expectedSelectedPaths)));
	}

	@Test
	public void testRenameWithLeftPositionAndOverwrite() {
		PositionalRenaming positionalPathRenamer = new OverwritingPositionalRenamer(
				PositionalRenamingDirection.LEFT, 4, "an");

		List<PairPath> selectedPathList = Arrays.asList(
				new PairPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadianar")),
				new PairPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadaanar")));

		assertThat(this.pathRenamer.rename(pathList, positionalPathRenamer),
				is(equalTo(selectedPathList)));

	}

	@Test
	public void testRenameWithRightPositionAndOverwrite() {
		PositionalRenaming positionalPathRenamer = new OverwritingPositionalRenamer(
				PositionalRenamingDirection.RIGHT, 4, "ole");

		List<PairPath> selectedPathList = Arrays.asList(
				new PairPath(Paths.get("/xyz/jadi.jar"), Paths
						.get("/xyz/jadolear")),
				new PairPath(Paths.get("/xyz/jada.jar"), Paths
						.get("/xyz/jadolear")));

		assertThat(this.pathRenamer.rename(pathList, positionalPathRenamer),
				is(equalTo(selectedPathList)));

	}
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testRenameWithNotValidMode() {
	// PositionalRenaming positionalPathRenamer = new PositionalRenaming(
	// "not valid mode", PositionalRenaming.RIGHT_SIDE, 4, "ole");
	//
	// this.pathRenamer.rename(pathList, positionalPathRenamer);
	//
	// }
}
