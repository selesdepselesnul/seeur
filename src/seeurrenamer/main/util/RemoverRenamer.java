package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * RemoverRenamer is a class that implements {@link Function}, it is a class
 * for removing any character from a {@link Path} by particular
 * {@link RenamerDirection} and particular position, it can be used
 * individually to one path or it can be used as an argument to method
 * {@link PathsRenamer#rename(java.util.List, Function)}
 * 
 * @author moch deden
 *
 */
public class RemoverRenamer implements Function<Path, Path> {

	private RenamerDirection renamingDirection;
	private int from;
	private int to;

	public RemoverRenamer(RenamerDirection renamingDirection, int from, int to) {
		this.renamingDirection = renamingDirection;
		this.from = from;
		this.to = to;
	}

	@Override
	public Path apply(Path path) throws IllegalArgumentException {
		StringBuilder stringBuilder = new StringBuilder(path.toString());

		if (this.renamingDirection.equals(RenamerDirection.RIGHT)) {
			stringBuilder.reverse();
			stringBuilder.delete(from, to);
			stringBuilder.reverse();
		} else if (this.renamingDirection.equals(RenamerDirection.LEFT)) {
			stringBuilder.delete(from, to);
		} else {
			throw new IllegalArgumentException();
		}

		return Paths.get(stringBuilder.toString());
	}

}
