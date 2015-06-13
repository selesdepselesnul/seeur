package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class PositionalRenaming implements Function<Path, Path> {

	public static final String RIGHT_SIDE = "from right";
	public static final String LEFT_SIDE = "from left";
	public static final String INSERT_OPERATION = "insert";
	public static final String OVERWRITE_OPERATION = "overwrite";
	String operation;
	String direction;
	private int position;
	private String newString;

	public PositionalRenaming(String operation, String direction, int position,
			String newString) {
		this.operation = operation;
		this.direction = direction;
		this.position = position;
		this.newString = newString;
	}

	@Override
	public Path apply(Path path) {
		Path newPathName = null;
		String pathString = path.toString();
		if (this.operation == INSERT_OPERATION) {
			if (this.direction == LEFT_SIDE) {

				String leftSide = pathString.substring(0, this.position);
				String rightSide = pathString.substring(this.position,
						pathString.length());

				newPathName = Paths.get((leftSide + newString + rightSide));
			} else {
				int bound = pathString.length() - this.position;
				String leftSide = pathString.substring(0, bound);
				String rightSide = pathString.substring(bound,
						pathString.length());
				newPathName = Paths.get((leftSide + newString + rightSide));
			}
		} else if (this.operation == OVERWRITE_OPERATION) {
			if (direction == LEFT_SIDE) {

				String unaffectedString = pathString
						.substring(0, this.position);
				String affectedString = pathString.substring(this.position,
						pathString.length());
				StringBuilder stringBuilder = new StringBuilder(affectedString);
				stringBuilder.replace(0, newString.length(), newString);
				newPathName = Paths.get(unaffectedString
						+ stringBuilder.toString());
			} else {

				int unaffectedStringEndIndex = (pathString.length() - this.position) - 1;
				String unaffectedString = pathString.substring(0,
						unaffectedStringEndIndex);
				String affectedString = pathString.substring(
						unaffectedStringEndIndex, pathString.length());
				StringBuilder stringBuilder = new StringBuilder(affectedString);
				stringBuilder.replace(0, newString.length(), newString);
				newPathName = Paths.get(unaffectedString
						+ stringBuilder.toString());
			}
		}
		return newPathName;
	}

}
