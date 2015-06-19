package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class PositionalRenaming implements Function<Path, Path> {

	public static final String RIGHT_SIDE = "from right";
	public static final String LEFT_SIDE = "from left";
	public static final String INSERTION_OPERATION = "insert";
	public static final String OVERWRITEN_OPERATION = "overwrite";
	private String operation;
	private String direction;
	private String newString;
	private String pathString;
	private int position;

	public PositionalRenaming(String operation, String direction, int position,
			String newString) {
		this.operation = operation;
		this.direction = direction;
		this.position = position;
		this.newString = newString;
	}

	@Override
	public Path apply(Path path) {
		this.pathString = path.toString();
		if (this.operation == INSERTION_OPERATION) {
			if (this.direction == LEFT_SIDE) {
				return Paths.get(insertLeft());
			} else if (this.direction == RIGHT_SIDE) {
				return Paths.get(insertRight());
			} else {
				throw new IllegalArgumentException();
			}
		} else if (this.operation == OVERWRITEN_OPERATION) {
			if (direction == LEFT_SIDE) {
				return Paths.get(overwriteLeft());
			} else if (this.direction == RIGHT_SIDE) {
				return Paths.get(overwriteRight());
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	private String overwriteRight() {

		int lefSideEndIndex = (this.pathString.length() - position) - 1;
		return overwrite(
				string -> string.substring(0, lefSideEndIndex),
				string -> string.substring(lefSideEndIndex, pathString.length()));
	}

	private String overwriteLeft() {
		return overwrite(string -> pathString.substring(0, this.position),
				string -> string.substring(this.position, pathString.length()));
	}

	private String insertRight() {
		int bound = pathString.length() - this.position;
		return insert(string -> string.substring(0, bound),
				string -> string.substring(bound, pathString.length()));
	}

	private String insertLeft() {
		return insert((string) -> string.substring(0, position),
				(string) -> string.substring(position));

	}

	private String insert(Function<String, String> leftSideExtractor,
			Function<String, String> rightSideExtractor) {

		return leftSideExtractor.apply(pathString) + newString
				+ rightSideExtractor.apply(pathString);

	}

	private String overwrite(Function<String, String> leftSideExtractor,
			Function<String, String> rightSideExtractor) {
		String leftSide = leftSideExtractor.apply(pathString);
		StringBuilder rightSideStringBuilder = new StringBuilder(
				rightSideExtractor.apply(pathString));
		rightSideStringBuilder.replace(0, newString.length(), newString);
		return leftSide + rightSideStringBuilder.toString();
	}
}
