package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.caseconverter.LowerConverter;
import seeurrenamer.main.util.caseconverter.PathCaseConverter;
import seeurrenamer.main.util.caseconverter.UnixStyleConverter;
import seeurrenamer.main.util.caseconverter.UpperConverter;

public class CaseConverterTest {

	private static PathCaseConverter pathCaseConverter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pathCaseConverter = new PathCaseConverter(
				Arrays.asList(new SelectedPath(Paths
						.get("/home/jaka/SutOno  supRapTo"))));
	}

	@Test
	public void testToLower() {
		LowerConverter lowerConverter = new LowerConverter();
		assertThat(pathCaseConverter.convertCase(lowerConverter),
				is(equalTo(Arrays.asList(new SelectedPath(Paths
						.get("/home/jaka/SutOno  supRapTo"), Paths
						.get("/home/jaka/sutono  suprapto"))))));
	}

	@Test
	public void testToUpper() {
		UpperConverter upperConverter = new UpperConverter();
		assertThat(pathCaseConverter.convertCase(upperConverter),
				is(equalTo(Arrays.asList(new SelectedPath(Paths
						.get("/home/jaka/SutOno  supRapTo"), Paths
						.get("/home/jaka/SUTONO  SUPRAPTO"))))));
	}

	@Test
	public void testToUnixStyle() {
		UnixStyleConverter unixStyleConverter = new UnixStyleConverter();
		assertThat(pathCaseConverter.convertCase(unixStyleConverter),
				is(equalTo(Arrays.asList(new SelectedPath(Paths
						.get("/home/jaka/SutOno  supRapTo"), Paths
						.get("/home/jaka/sutono_suprapto"))))));
	}

}
