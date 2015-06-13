package seeurrenamer.main.util.caseconverter;

import java.util.function.Function;

public class UpperConverter implements Function<String, String> {

	@Override
	public String toString() {
		return "upper";
	}

	@Override
	public String apply(String string) {
		return string.toUpperCase();
	}

}
