package seeurrenamer.main.util.sequence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class AlphabetSequenceRenaming implements Function<Path, Path> {

	char currentValue = 'a' - 1;

	@Override
	public Path apply(Path path) {
		currentValue++;
		if (currentValue > 'z') {
			currentValue = 'a';
		}
		return Paths.get(currentValue + "." + path);
	}

	@Override
	public String toString() {
		return "a., b., c., ...";
	}

}
