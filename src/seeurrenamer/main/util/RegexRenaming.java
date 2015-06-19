package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import seeurrenamer.main.model.PairPath;

/**
 * RegexRenaming is a class that can be used for renaming path with regex and
 * replacer schema this class is a class that implements Function, it can be
 * used indivually for one path or it can be used for renaming {@link List} of
 * {@link PairPath} as an argument to the method
 * {@link PathsRenamer#rename(List, Function)}
 * 
 * @author moch deden
 *
 */
public class RegexRenaming implements Function<Path, Path> {

	private String regex;
	private String replacer;
	private boolean isCaseInsensitive;

	/**
	 * Initialize RegexRenaming with regex, replacer and decide either the regex
	 * engine use case sensitive or case insensitive
	 * 
	 * @param regex
	 *            string for regular expression
	 * @param replacer
	 *            string for replacing
	 * @param isCaseInsensitive
	 *            boolean value to indicate case sensitivity of regex engine
	 */
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