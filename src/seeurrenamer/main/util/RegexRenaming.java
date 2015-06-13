package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class RegexRenaming implements Function<Path, Path> {

	private String regex;
	private boolean isCaseInsensitive;
	private String replacer;

	public RegexRenaming(String regex) {
		this.regex = regex;
	}

	public RegexRenaming(String regex, boolean isCaseInsensitive) {
		this.regex = regex;
		this.isCaseInsensitive = isCaseInsensitive;
	}

	public RegexRenaming(String regex, String replacer) {
		this.regex = regex;
		this.replacer = replacer;
	}

	public RegexRenaming(String regex, String replacer,
			boolean isCaseInsensitive) {
		this.regex = regex;
		this.replacer = replacer;
		this.isCaseInsensitive = isCaseInsensitive;
	}

	@Override
	public Path apply(Path path) {
		if (this.isCaseInsensitive) {
			this.regex = "(?i)" + this.regex;
		}
		return Paths.get(path.toString().replaceAll(this.regex, this.replacer));

	}
}