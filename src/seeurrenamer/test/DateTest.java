package seeurrenamer.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTest {

	public static void main(String[] args) throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
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
