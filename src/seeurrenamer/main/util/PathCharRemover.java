package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathCharRemover {

	public static final String LEFT = "delete from left";
	public static final String RIGHT = "delete from right";

	public Path remove(Path path, String direction, int from, int to) {
		StringBuilder stringBuilder = new StringBuilder(path.toString());

		if (direction == RIGHT) {
			stringBuilder.reverse();
			stringBuilder.delete(from, to);
			stringBuilder.reverse();
		} else {
			stringBuilder.delete(from, to);
		}

		return Paths.get(stringBuilder.toString());
	}

}
