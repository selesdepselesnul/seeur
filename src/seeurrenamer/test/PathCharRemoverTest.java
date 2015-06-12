package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seeurrenamer.main.util.PathCharRemover;

public class PathCharRemoverTest {

	private PathCharRemover pathCharRemover;

	@Before
	public void setUp() throws Exception {
		pathCharRemover = new PathCharRemover();
	}

	@Test
	public void testRemoveFromLeft() {
		assertThat(this.pathCharRemover.remove(Paths.get("aku adalah"),
				PathCharRemover.LEFT, 0, 4), is(equalTo(Paths.get("adalah"))));
	}

	@Test
	public void testRemoveFromRight() {
		assertThat(this.pathCharRemover.remove(Paths.get("aku adalah"),
				PathCharRemover.RIGHT, 0, 7), is(equalTo(Paths.get("aku"))));
	}

}
