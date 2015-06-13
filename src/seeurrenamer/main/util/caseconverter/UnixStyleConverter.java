package seeurrenamer.main.util.caseconverter;

import java.util.function.Function;

public class UnixStyleConverter implements Function<String, String> {

	private static final String U_SCORE_AT_THE_END = "_+$";
	private static final String MORE_THAN_ONE_U_SCORE = "_{2,}";
	private static final String WHITE_SPACE_OR_PUNCT = "\\s+|\\p{Punct}";

	@Override
	public String apply(String string) {
		return string != null ? string.toLowerCase()
				.replaceAll(WHITE_SPACE_OR_PUNCT, "_")
				.replaceAll(MORE_THAN_ONE_U_SCORE, "_")
				.replaceAll(U_SCORE_AT_THE_END, "") : string;
	}

	@Override
	public String toString() {
		return "unix-style";
	}

}
