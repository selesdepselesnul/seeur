package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import seeurrenamer.main.model.SelectedPath;

public class RegexPathRenamer extends PathRenamer {

	private String regex;

	public RegexPathRenamer(String regex) {
		this.regex = regex;
	}

	@Override
	protected Path renamePath(SelectedPath selectedPath, String newString) {
		return Paths.get(selectedPath.getBefore().toString()
				.replaceAll(this.regex, newString));

	}
	// public List<SelectedPath> rename(List<SelectedPath> pathList,
	// String newString) {
	// List<SelectedPath> selectedPathList = new ArrayList<>();
	// pathList.stream().forEach(
	// selectedPath -> {
	// Path newFullPath = extractNewPath(
	// selectedPath,
	// selectedPath.getBefore().toString()
	// .replaceAll(this.regex, newString));
	// selectedPathList.add(new SelectedPath(selectedPath
	// .getBeforeFullPath(), newFullPath));
	// });
	// return selectedPathList;
	// }
	//
	// private Path extractNewPath(SelectedPath selectedPath,
	// String newStringPathName) {
	// return Paths.get(selectedPath.getBeforeFullPath().getParent()
	// .toString()
	// + "/" + newStringPathName);
	// }

}