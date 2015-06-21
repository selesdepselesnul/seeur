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
import seeurrenamer.main.util.sequence.AlphabetSequenceRenamer;
import seeurrenamer.main.util.sequence.DecimalSequenceRenamer;

public class SequenceAdderTest {

	private static PathsRenamer pathRenamer;
	private static List<PairPath> pairPathList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pathRenamer = new PathsRenamer();
		pairPathList = Arrays.asList(new PairPath(Paths.get("/test/sudono")),
				new PairPath(Paths.get("/test/sudani")),
				new PairPath(Paths.get("/test/sudeni")));
	}

	@Test
	public void testAddWithDecimalSequence() {
		DecimalSequenceRenamer decimalSequence = new DecimalSequenceRenamer();
		assertThat(pathRenamer.rename(pairPathList, decimalSequence),
				is(equalTo(Arrays.asList(
						new PairPath(Paths.get("/test/sudono"), Paths
								.get("/test/1.sudono")),
						new PairPath(Paths.get("/test/sudani"), Paths
								.get("/test/2.sudani")),
						new PairPath(Paths.get("/test/sudeni"), Paths
								.get("/test/3.sudeni"))))));
	}

	@Test
	public void testAddWithAlphabetSequence() {
		AlphabetSequenceRenamer alphabetSequence = new AlphabetSequenceRenamer();
		assertThat(pathRenamer.rename(pairPathList, alphabetSequence),
				is(equalTo(Arrays.asList(
						new PairPath(Paths.get("/test/sudono"), Paths
								.get("/test/a.sudono")),
						new PairPath(Paths.get("/test/sudani"), Paths
								.get("/test/b.sudani")),
						new PairPath(Paths.get("/test/sudeni"), Paths
								.get("/test/c.sudeni"))))));
	}

}
