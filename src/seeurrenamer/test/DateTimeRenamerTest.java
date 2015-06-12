package seeurrenamer.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DateTimeRenamerTest {

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
	public void testDateTimeFormatter() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// System.out.println(LocalDate.now().format(formatter));

	}

	@Test
	public void testBasicFileAtribut() throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		BasicFileAttributes attrs = Files.readAttributes(
				Paths.get("/home/morrisseymarr/connector-cpp-en.pdf"),
				BasicFileAttributes.class);
		String creationTime = attrs.creationTime().toString();
		System.out.println("creation time : " + creationTime);
		LocalDate formattedVersion = LocalDate.parse(creationTime, formatter);
		System.out.println("formatted creation time : "
				+ formattedVersion);
	}
}
