package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import seeurrenamer.main.model.SelectedPath;

public class RegexPathRenamer extends PathRenamer {

	private String regex;
	private boolean isCaseInsensitive;

	public RegexPathRenamer(String regex) {
		this.regex = regex;
	}

	public RegexPathRenamer(String regex, boolean isCaseInsensitive) {
		this.regex = regex;
		this.isCaseInsensitive = isCaseInsensitive;
	}

	@Override
	protected Path renamePath(SelectedPath selectedPath, String newString) {
		if (this.isCaseInsensitive) {
			this.regex = "(?i)" + this.regex;
		}
		return Paths.get(selectedPath.getBefore().toString()
				.replaceAll(this.regex, newString));

	}
}