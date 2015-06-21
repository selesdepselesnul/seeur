package seeurrenamer.main.util.positionalrenaming;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.RenamerDirection;

/**
 * This class is a child class of {@link PositionalRenamer}, it can be used to
 * insert newString to path with particular {@link RenamerDirection} and
 * particular position, this class can be used individually or it can be used as
 * an argument for method {@link PathsRenamer#rename(java.util.List, Function)}
 * 
 * @author moch deden
 *
 */
public class InsertionPositionalRenamer extends PositionalRenamer {

	public InsertionPositionalRenamer() {
		super();
	}

	public InsertionPositionalRenamer(RenamerDirection direction, int position,
			String newString) {
		super(direction, position, newString);
	}

	@Override
	protected Path renameRight() {
		int bound = getPathString().length() - getPosition();
		return Paths.get(insert(string -> string.substring(0, bound),
				string -> string.substring(bound, getPathString().length())));
	}

	@Override
	protected Path renameLeft() {
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
