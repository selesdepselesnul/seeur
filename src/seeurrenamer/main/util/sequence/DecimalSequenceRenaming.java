package seeurrenamer.main.util.sequence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class DecimalSequenceRenaming implements Function<Path, Path> {

	int startValue = 0;

	@Override
	public Path apply(Path path) {
		startValue++;
		return Paths.get(startValue + "." + path);
	}

	@Override
	public String toString() {
		return "1., 2., 3., ...";
	}
	
	

}
