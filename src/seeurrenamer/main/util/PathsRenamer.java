package seeurrenamer.main.util;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import seeurrenamer.main.model.PairPath;

public class PathsRenamer {

	private Path extractNewPath(PairPath pairPath, Path newPathName) {
		return pairPath.getBeforeFullPath().getParent().resolve(newPathName);
	}

	public List<PairPath> rename(List<PairPath> pairPathList,
			Function<Path, Path> renamingMethod) {
		return pairPathList
				.stream()
				.map(pairPath -> {
					Path newFullPath = extractNewPath(pairPath,
							renamingMethod.apply(pairPath.getBeforeFileName()));
					return new PairPath(pairPath.getBeforeFullPath(),
							newFullPath);
				}).collect(Collectors.toList());

	}

}
