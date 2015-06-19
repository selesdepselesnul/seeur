package seeurrenamer.main.util.positionalrenaming;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class OverwritingPositionalRenamer extends PositionalRenaming {

	public OverwritingPositionalRenamer() {
	}

	public OverwritingPositionalRenamer(PositionalRenamingDirection direction, int position,
			String newString) {
		super(direction, position, newString);
	}

	@Override
	public Path renameRight() {
		int lefSideEndIndex = (getPathString().length() - getPosition()) - 1;
		return Paths.get(overwrite(string -> string.substring(0,
				lefSideEndIndex), string -> string.substring(lefSideEndIndex,
				getPathString().length())));
	}

	@Override
	public Path renameLeft() {
		return Paths.get(overwrite(
				string -> getPathString().substring(0, getPosition()),
				string -> string.substring(getPosition(), getPathString()
						.length())));
	}

	private String overwrite(Function<String, String> leftSideExtractor,
			Function<String, String> rightSideExtractor) {
		String leftSide = leftSideExtractor.apply(getPathString());
		StringBuilder rightSideStringBuilder = new StringBuilder(
				rightSideExtractor.apply(getPathString()));
		rightSideStringBuilder.replace(0, getNewString().length(),
				getNewString());
		return leftSide + rightSideStringBuilder.toString();
	}

	@Override
	public String toString() {
		return "overwrite";
	}

}
