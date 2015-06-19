package seeurrenamer.main.util.positionalrenaming;

import java.nio.file.Path;
import java.util.function.Function;

import seeurrenamer.main.util.PathsRenamer;

/**
 * PositionalRenaming is a concrete class that implements {@link Function}, it
 * can be used for renaming individual path or it can be used as an argument to
 * parameter renamingMethod of method
 * {@link PathsRenamer#rename(java.util.List, Function)}
 * 
 * @author moch deden
 *
 */
public abstract class PositionalRenaming implements Function<Path, Path> {

	private PositionalRenamingDirection direction;
	private String newString;
	private String pathString;
	private int position;

	/**
	 * Initializes object of class {@link PositionalRenaming} with specified
	 * operation, direction, position dan the newString to insert or to
	 * overwrite
	 * 
	 * @param direction
	 *            direction of operation, either it can be left or right
	 * @param position
	 *            position of inserting or overwriting, it's used zero based
	 *            index
	 * @param newString
	 *            newString to either inserting or overwriting
	 */
	public PositionalRenaming(PositionalRenamingDirection direction,
			int position, String newString) {
		this.direction = direction;
		this.position = position;
		this.newString = newString;
	}

	public PositionalRenaming() {
		this.direction = PositionalRenamingDirection.LEFT;
		this.position = 0;
		this.newString = "";
	}

	@Override
	final public Path apply(Path path) {
		this.pathString = path.toString();
		if (this.direction.equals(PositionalRenamingDirection.LEFT)) {
			return renameLeft();
		} else if (this.direction.equals(PositionalRenamingDirection.RIGHT)) {
			return renameRight();
		} else {
			throw new IllegalArgumentException();
		}
	}

	public abstract Path renameRight();

	public abstract Path renameLeft();

	public PositionalRenamingDirection getDirection() {
		return direction;
	}

	public void setDirection(PositionalRenamingDirection direction) {
		this.direction = direction;
	}

	public String getNewString() {
		return newString;
	}

	public void setNewString(String newString) {
		this.newString = newString;
	}

	protected String getPathString() {
		return pathString;
	}

	protected void setPathString(String pathString) {
		this.pathString = pathString;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
