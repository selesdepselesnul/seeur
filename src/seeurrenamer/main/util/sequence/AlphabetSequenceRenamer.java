package seeurrenamer.main.util.sequence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * @author moch deden
 *
 */
public class AlphabetSequenceRenamer implements Function<Path, Path> {

	private char currentValue = 'a' - 1;

	@Override
	public Path apply(Path path) {
		this.currentValue++;
		if (this.currentValue >= 'z' + 1) { // reset to 'a'
			this.currentValue = 'a';
		}
		return Paths.get(this.currentValue + "." + path);
	}

	@Override
	public String toString() {
		return "a., b., c., ...";
	}

}
