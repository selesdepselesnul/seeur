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
import seeurrenamer.main.util.caseconverter.LowerConverter;
import seeurrenamer.main.util.caseconverter.UnixStyleConverter;
import seeurrenamer.main.util.caseconverter.UpperConverter;

public class CaseConverterTest {

	private static PathsRenamer pathRenamer;
	private static List<PairPath> pairPathList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pairPathList = Arrays.asList(new PairPath(Paths
				.get("/home/jaka/SutOno  supRapTo")));
		pathRenamer = new PathsRenamer();
	}

	@Test
	public void testRenameWithLowerConverter() {
		LowerConverter lowerConverter = new LowerConverter();
		assertThat(pathRenamer.rename(pairPathList, lowerConverter),
				is(equalTo(Arrays.asList(new PairPath(Paths
						.get("/home/jaka/SutOno  supRapTo"), Paths
						.get("/home/jaka/sutono  suprapto"))))));
	}

	@Test
	public void testRenameWithUpperConverter() {
		UpperConverter upperConverter = new UpperConverter();
		assertThat(pathRenamer.rename(pairPathList, upperConverter),
				is(equalTo(Arrays.asList(new PairPath(Paths
						.get("/home/jaka/SutOno  supRapTo"), Paths
						.get("/home/jaka/SUTONO  SUPRAPTO"))))));
	}

	@Test
	public void testRenameWithUnixStyleConverter() {
		UnixStyleConverter unixStyleConverter = new UnixStyleConverter();
		assertThat(pathRenamer.rename(pairPathList, unixStyleConverter),
				is(equalTo(Arrays.asList(new PairPath(Paths
						.get("/home/jaka/SutOno  supRapTo"), Paths
						.get("/home/jaka/sutono_suprapto"))))));
	}

}
