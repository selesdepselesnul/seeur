package seeurrenamer.main.util.caseconverter;

import java.util.function.Function;

public class LowerConverter implements Function<String, String> {

	@Override
	public String apply(String string) {
		return string.toLowerCase();
	}

	@Override
	public String toString() {
		return "lower";
	}

}
