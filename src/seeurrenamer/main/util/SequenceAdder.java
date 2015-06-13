package seeurrenamer.main.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.model.Sequence;

public class SequenceAdder {

	private List<SelectedPath> selectedPathList;
	private List<SelectedPath> newSelectedPathList;

	public SequenceAdder(List<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}

	public <T> List<SelectedPath> add(Sequence<T> sequence) {
		this.newSelectedPathList = new ArrayList<>();
		for (SelectedPath selectedPath : this.selectedPathList) {
			Path newPathName = (Paths.get(sequence.getCurrentValue().toString()
					+ "." + selectedPath.getBefore()));

			newSelectedPathList.add(new SelectedPath(selectedPath
					.getBeforeFullPath(), PathRenamer.extractNewPath(
					selectedPath, newPathName)));

			sequence.add();
		}
		return newSelectedPathList;

	}

}
