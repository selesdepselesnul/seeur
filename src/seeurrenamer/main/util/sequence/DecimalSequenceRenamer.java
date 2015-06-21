package seeurrenamer.main.util.sequence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * @author moch deden
 *
 */
public class DecimalSequenceRenamer implements Function<Path, Path> {

	private int startValue = 0;

	@Override
	public Path apply(Path path) {
		this.startValue++;
		return Paths.get(this.startValue + "." + path);
	}

	@Override
	public String toString() {
		return "1., 2., 3., ...";
	}
	
	

}
