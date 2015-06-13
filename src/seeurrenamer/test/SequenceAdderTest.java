package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.model.Sequence;
import seeurrenamer.main.util.SequenceAdder;

public class SequenceAdderTest {

	private static SequenceAdder sequenceAdder;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sequenceAdder = new SequenceAdder(Arrays.asList(
				new SelectedPath(Paths.get("/test/sudono")), new SelectedPath(
						Paths.get("/test/sudani")),
				new SelectedPath(Paths.get("/test/sudeni"))));
	}

	@Test
	public void testAddWithDecimalSystem() {
		Sequence<Integer> decimalSequence = new Sequence<>(1, 3,
				integer -> integer + 1, "1., 2. , 3. , ...");
		assertThat(sequenceAdder.add(decimalSequence),
				is(equalTo(Arrays.asList(
						new SelectedPath(Paths.get("/test/sudono"), Paths
								.get("/test/1.sudono")),
						new SelectedPath(Paths.get("/test/sudani"), Paths
								.get("/test/2.sudani")),
						new SelectedPath(Paths.get("/test/sudeni"), Paths
								.get("/test/1.sudeni"))))));
	}

	@Test
	public void testAddWithAlphabet() {
		Sequence<Character> alphabetSequence = new Sequence<>('a', 'c',
				character -> ++character, "a., b., c., ...");
		assertThat(sequenceAdder.add(alphabetSequence),
				is(equalTo(Arrays.asList(
						new SelectedPath(Paths.get("/test/sudono"), Paths
								.get("/test/a.sudono")),
						new SelectedPath(Paths.get("/test/sudani"), Paths
								.get("/test/b.sudani")),
						new SelectedPath(Paths.get("/test/sudeni"), Paths
								.get("/test/a.sudeni"))))));
	}

}
