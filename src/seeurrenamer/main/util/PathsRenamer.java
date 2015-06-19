package seeurrenamer.main.util;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import seeurrenamer.main.model.PairPath;

/**
 * it is a class for renaming {@link List} of {@link PairPath} with any renaming
 * method
 * 
 * @author moch deden
 */
public class PathsRenamer {

	private Path extractNewPath(PairPath pairPath, Path newPathName) {
		return pairPath.getBeforeFullPath().getParent().resolve(newPathName);
	}

	/**
	 * return {@link List} of {@link PairPath} that is already renamed
	 * 
	 * @param pairPathList
	 *            {@link List} of {@link PairPath} to be renaming
	 * @param renamingMethod
	 *            method for renaming pairPathList, this method implements
	 *            {@link Function}
	 * @return {@link List} of {@link PairPath} that is already renamed
	 */
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
