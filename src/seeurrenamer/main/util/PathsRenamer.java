package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import seeurrenamer.main.model.PairPath;

public class PathsRenamer {

	private Path extractNewPath(PairPath pairPath, Path newPathName) {
		return Paths.get(pairPath.getBeforeFullPath().getParent().toString()
				+ "/" + newPathName.toString());
	}

	public List<PairPath> rename(List<PairPath> pairPathList,
			Function<Path, Path> renamingMethod) {
		List<PairPath> newPairPathList = new ArrayList<>();

		pairPathList.stream().forEach(
				pairPath -> {
					Path newFullPath = extractNewPath(pairPath,
							renamingMethod.apply(pairPath.getBefore()));
					newPairPathList.add(new PairPath(pairPath.getBeforeFullPath(),
							newFullPath));
				});
		return newPairPathList;
	}

}
