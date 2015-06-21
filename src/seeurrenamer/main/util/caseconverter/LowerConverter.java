package seeurrenamer.main.util.caseconverter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;


/**
 * @author moch deden
 *
 */
public class LowerConverter implements Function<Path, Path> {

	@Override
	public String toString() {
		return "lower";
	}

	@Override
	public Path apply(Path path) {
		return Paths.get(path.toString().toLowerCase());
	}

}
