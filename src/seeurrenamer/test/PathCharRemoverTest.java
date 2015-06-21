package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.RemoverRenamer;
import seeurrenamer.main.util.RenamerDirection;

public class PathCharRemoverTest {

	private static PathsRenamer pathRenamer;
	private RemoverRenamer pathCharRemover;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pathRenamer = new PathsRenamer();
	}

	@Test
	public void testRemoveFromLeft() {
		pathCharRemover = new RemoverRenamer(RenamerDirection.LEFT, 0, 4);
		List<PairPath> pairPathList = Arrays.asList(new PairPath(Paths
				.get("/test/aku adalah")));
		pathRenamer.rename(pairPathList, pathCharRemover);
		assertThat(pathRenamer.rename(pairPathList, pathCharRemover),
				is(equalTo(Arrays.asList(new PairPath(pairPathList.get(0)
						.getBeforeFullPath(), Paths.get("/test/adalah"))))));
	}

	@Test
	public void testRemoveFromRight() {
		pathCharRemover = new RemoverRenamer(RenamerDirection.RIGHT, 0, 7);
		List<PairPath> pairPathList = Arrays.asList(new PairPath(Paths
				.get("/test/aku adalah")));
		pathRenamer.rename(pairPathList, pathCharRemover);
		assertThat(pathRenamer.rename(pairPathList, pathCharRemover),
				is(equalTo(Arrays.asList(new PairPath(pairPathList.get(0)
						.getBeforeFullPath(), Paths.get("/test/aku"))))));

	}

}
