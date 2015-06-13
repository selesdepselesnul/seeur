package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class RemoverRenaming implements Function<Path, Path> {

	public static final String LEFT = "delete from left";
	public static final String RIGHT = "delete from right";
	private String direction;
	private int from;
	private int to;

	public RemoverRenaming(String direction, int from, int to) {
		this.direction = direction;
		this.from = from;
		this.to = to;
	}

	@Override
	public Path apply(Path path) {
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
