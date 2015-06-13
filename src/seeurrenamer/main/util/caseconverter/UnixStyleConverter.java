package seeurrenamer.main.util.caseconverter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class UnixStyleConverter implements Function<Path, Path> {

	private static final String U_SCORE_AT_THE_END = "_+$";
	private static final String MORE_THAN_ONE_U_SCORE = "_{2,}";
	private static final String WHITE_SPACE_OR_PUNCT = "\\s+|\\p{Punct}";

	@Override
	public String toString() {
		return "unix-style";
	}

	@Override
	public Path apply(Path path) {
		return path.toString() != null ? Paths.get(path.toString()
				.toLowerCase().replaceAll(WHITE_SPACE_OR_PUNCT, "_")
				.replaceAll(MORE_THAN_ONE_U_SCORE, "_")
				.replaceAll(U_SCORE_AT_THE_END, "")) : path;
	}

}
