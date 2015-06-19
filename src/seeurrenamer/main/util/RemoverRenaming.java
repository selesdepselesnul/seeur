package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * RemoverRenaming is a class that implements {@link Function}, it is a class for
 * removing any character from a {@link Path} by particular direction and
 * particular position, it can be used individually to one path or it can be
 * used as an argument to method
 * {@link PathsRenamer#rename(java.util.List, Function)}
 * 
 * @author moch deden
 *
 */
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
	public Path apply(Path path) throws IllegalArgumentException {
		StringBuilder stringBuilder = new StringBuilder(path.toString());

		if (direction == RIGHT) {
			stringBuilder.reverse();
			stringBuilder.delete(from, to);
			stringBuilder.reverse();
		} else if (direction == LEFT) {
			stringBuilder.delete(from, to);
		} else {
			throw new IllegalArgumentException();
		}

		return Paths.get(stringBuilder.toString());
	}

}
