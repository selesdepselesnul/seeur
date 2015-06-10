package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import seeurrenamer.main.model.SelectedPath;

public abstract class PathRenamer {

	public static Path extractNewPath(SelectedPath selectedPath,
			Path newPathName) {
		return Paths.get(selectedPath.getBeforeFullPath().getParent()
				.toString()
				+ "/" + newPathName.toString());
	}

	public final List<SelectedPath> rename(List<SelectedPath> pathList,
			String newString) {
		List<SelectedPath> selectedPathList = new ArrayList<>();

		pathList.stream().forEach(
				selectedPath -> {
					Path newFullPath = extractNewPath(selectedPath,
							renamePath(selectedPath, newString));
					selectedPathList.add(new SelectedPath(selectedPath
							.getBeforeFullPath(), newFullPath));
				});
		return selectedPathList;
	}

	protected abstract Path renamePath(SelectedPath selectedPath,
			String newString);

}
