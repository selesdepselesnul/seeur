package seeurrenamer.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import seeurrenamer.main.util.PathCharRemover;

public class PathCharRemoverTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRemoveFromLeft() {
		PathCharRemover pathCharRemover = new PathCharRemover();
		assertThat(pathCharRemover.remove(Paths.get("aku adalah"),
				PathCharRemover.LEFT, 0, 3), is(equalTo(Paths.get("adalah"))));
	}

	@Test
	public void testRemoveFromRight() {
		PathCharRemover pathCharRemover = new PathCharRemover();
		assertThat(pathCharRemover.remove(Paths.get("aku adalah"),
				PathCharRemover.RIGHT, 0, 6), is(equalTo(Paths.get("aku"))));
	}

}
