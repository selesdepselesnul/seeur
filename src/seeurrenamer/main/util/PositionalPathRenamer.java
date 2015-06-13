package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import seeurrenamer.main.model.SelectedPath;

public class PositionalPathRenamer extends PathRenamer {

	public static final String RIGHT_SIDE = "from right";
	public static final String LEFT_SIDE = "from left";
	public static final String INSERT_OPERATION = "insert";
	public static final String OVERWRITE_OPERATION = "overwrite";
	String operation;
	String direction;
	private int position;

	public PositionalPathRenamer(String operation, String direction,
			int position) {
		this.operation = operation;
		this.direction = direction;
		this.position = position;
	}

	@Override
	protected Path renamePath(SelectedPath selectedPath, String newString) {

		Path newPathName = null;
		if (this.operation == INSERT_OPERATION) {
			if (this.direction == LEFT_SIDE) {
				Path path = selectedPath.getBefore();

				String leftSide = path.toString().substring(0, this.position);
				String rightSide = path.toString().substring(this.position,
						path.toString().length());

				newPathName = Paths.get((leftSide + newString + rightSide));
			} else {
				Path path = selectedPath.getBefore();
				int bound = path.toString().length() - this.position;
				String leftSide = path.toString().substring(0, bound);
				String rightSide = path.toString().substring(bound,
						path.toString().length());
				newPathName = Paths.get((leftSide + newString + rightSide));
			}
		} else if (this.operation == OVERWRITE_OPERATION) {
			if (direction == LEFT_SIDE) {
				String stringPath = selectedPath.getBefore().toString();

				String unaffectedString = stringPath
						.substring(0, this.position);
				String affectedString = stringPath.substring(this.position,
						stringPath.length());
				StringBuilder stringBuilder = new StringBuilder(affectedString);
				stringBuilder.replace(0, newString.length(), newString);
				newPathName = Paths.get(unaffectedString
						+ stringBuilder.toString());
			} else {

				String stringPath = selectedPath.getBefore().toString();
				int unaffectedStringEndIndex = (stringPath.length() - this.position) - 1;
				String unaffectedString = stringPath.substring(0,
						unaffectedStringEndIndex);
				String affectedString = stringPath.substring(
						unaffectedStringEndIndex, stringPath.length());
				StringBuilder stringBuilder = new StringBuilder(affectedString);
				stringBuilder.replace(0, newString.length(), newString);
				newPathName = Paths.get(unaffectedString
						+ stringBuilder.toString());
			}
		}
		return newPathName;
	}

}
