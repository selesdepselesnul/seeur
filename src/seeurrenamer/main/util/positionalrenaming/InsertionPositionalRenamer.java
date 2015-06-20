package seeurrenamer.main.util.positionalrenaming;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import seeurrenamer.main.util.RenamingDirection;

public class InsertionPositionalRenamer extends PositionalRenaming {

	public InsertionPositionalRenamer() {

	}

	public InsertionPositionalRenamer(RenamingDirection direction, int position,
			String newString) {
		super(direction, position, newString);
	}

	@Override
	public Path renameRight() {
		int bound = getPathString().length() - getPosition();
		return Paths.get(insert(string -> string.substring(0, bound),
				string -> string.substring(bound, getPathString().length())));
	}

	@Override
	public Path renameLeft() {
		return Paths.get(insert((string) -> string.substring(0, getPosition()),
				(string) -> string.substring(getPosition())));
	}

	private String insert(Function<String, String> leftSideExtractor,
			Function<String, String> rightSideExtractor) {
		return leftSideExtractor.apply(getPathString()) + getNewString()
				+ rightSideExtractor.apply(getPathString());
	}

	@Override
	public String toString() {
		return "insert";
	}

}
