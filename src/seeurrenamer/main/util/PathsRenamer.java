package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import seeurrenamer.main.model.PairPath;

public class PathsRenamer {

	private Path extractNewPath(PairPath selectedPath, Path newPathName) {
		return Paths.get(selectedPath.getBeforeFullPath().getParent()
				.toString()
				+ "/" + newPathName.toString());
	}

	public List<PairPath> rename(List<PairPath> pathList,
			Function<Path, Path> renamingMethod) {
		List<PairPath> pairPathList = new ArrayList<>();

		pathList.stream().forEach(
				pairPath -> {
					Path newFullPath = extractNewPath(pairPath,
							renamingMethod.apply(pairPath.getBefore()));
					pairPathList.add(new PairPath(pairPath.getBeforeFullPath(),
							newFullPath));
				});
		return pairPathList;
	}

}
