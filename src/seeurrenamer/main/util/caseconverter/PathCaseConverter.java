package seeurrenamer.main.util.caseconverter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.PathRenamer;

public class PathCaseConverter {

	private List<SelectedPath> selectedPathList;
	private List<SelectedPath> newSelectedPathList;

	public PathCaseConverter(List<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}

	public List<SelectedPath> convertCase(Function<String, String> caseConverter) {
		this.newSelectedPathList = new ArrayList<>();
		this.selectedPathList.stream().forEach(
				selectedPath -> {
					Path newPath = Paths.get(caseConverter.apply(selectedPath
							.getBefore().toString()));
					newSelectedPathList.add(new SelectedPath(selectedPath
							.getBeforeFullPath(), PathRenamer.extractNewPath(
							selectedPath, newPath)));
				});
		return newSelectedPathList;

	}

}
