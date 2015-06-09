package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import seeurrenamer.main.model.SelectedPath;

public class PathRenamer {

	public static final String RIGHT_SIDE = "from right";
	public static final String LEFT_SIDE = "from left";
	public static final String INSERT_OPERATION = "insert";
	public static final String OVERWRITE_OPERATION = "overwrite";

	public PathRenamer() {
	}

	public List<SelectedPath> rename(List<SelectedPath> pathList, int pos,
			String newString, String direction, String operationMode) {
		List<SelectedPath> selectedPathList = new ArrayList<>();
		if (operationMode == INSERT_OPERATION) {
			if (direction == LEFT_SIDE) {
				pathList.stream()
						.forEach(
								selectedPath -> {
									Path path = selectedPath.getBefore();

									String leftSide = path.toString()
											.substring(0, pos);
									String rightSide = path.toString()
											.substring(pos,
													path.toString().length());
									Path newPath = Paths
											.get(selectedPath
													.getBeforeFullPath()
													.getParent().toString()
													+ "/"
													+ (leftSide + newString + rightSide));
									selectedPathList.add(new SelectedPath(
											selectedPath.getBeforeFullPath(),
											selectedPath.getBeforeFullPath()
													.resolve(newPath)));
								});
			} else {
				pathList.stream()
						.forEach(
								selectedPath -> {
									Path path = selectedPath.getBefore();
									int bound = path.toString().length() - pos;
									String leftSide = path.toString()
											.substring(0, bound);
									String rightSide = path.toString()
											.substring(bound,
													path.toString().length());
									String newStringPathName = (leftSide
											+ newString + rightSide);
									;
									selectedPathList.add(new SelectedPath(
											selectedPath.getBeforeFullPath(),
											extractNewPath(selectedPath,
													newStringPathName)));

								});

			}
		} else if (operationMode == OVERWRITE_OPERATION) {
			if (direction == LEFT_SIDE) {
				pathList.stream().forEach(
						selectedPath -> {
							String stringPath = selectedPath.getBefore()
									.toString();

							String unaffectedString = stringPath.substring(0,
									pos);
							System.out.println("unaffected string : "
									+ unaffectedString);
							String affectedString = stringPath.substring(pos,
									stringPath.length());
							System.out.println("affected string  : "
									+ affectedString);
							StringBuilder stringBuilder = new StringBuilder(
									affectedString);
							stringBuilder.replace(0, newString.length(),
									newString);
							String newStringPathName = unaffectedString
									+ stringBuilder.toString();
							System.out.println("after : " + newStringPathName);
							selectedPathList.add(new SelectedPath(selectedPath
									.getBeforeFullPath(), extractNewPath(
									selectedPath, newStringPathName)));

						});
			} else {
				pathList.stream()
						.forEach(
								selectedPath -> {

									String stringPath = selectedPath
											.getBefore().toString();
									int unaffectedStringEndIndex = (stringPath
											.length() - pos) - 1;
									String unaffectedString = stringPath
											.substring(0,
													unaffectedStringEndIndex);
									System.out.println("unaffected string : "
											+ unaffectedString);
									String affectedString = stringPath
											.substring(
													unaffectedStringEndIndex,
													stringPath.length());
									System.out.println("affected string : "
											+ affectedString);
									StringBuilder stringBuilder = new StringBuilder(
											affectedString);
									stringBuilder.replace(0,
											newString.length() , newString);
									String newStringPathName = unaffectedString
											+ stringBuilder.toString();
									System.out.println("after : "
											+ newStringPathName);
									selectedPathList.add(new SelectedPath(
											selectedPath.getBeforeFullPath(),
											extractNewPath(selectedPath,
													newStringPathName)));

								});

			}
		}
		return selectedPathList;
	}

	private Path extractNewPath(SelectedPath selectedPath,
			String newStringPathName) {
		return Paths.get(selectedPath.getBeforeFullPath().getParent()
				.toString()
				+ "/" + newStringPathName);
	}
}
