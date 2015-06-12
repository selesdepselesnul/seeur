package seeurrenamer.main.controller;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.PathCharRemover;
import seeurrenamer.main.util.PathRenamer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class RemovingCharManipulatorController implements Initializable {

	@FXML
	private ComboBox<String> directionComboBox;

	@FXML
	private Spinner<Integer> fromSpinner;

	@FXML
	private Spinner<Integer> toSpinner;

	private ObservableList<SelectedPath> selectedPathList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.directionComboBox.getItems().setAll(PathCharRemover.LEFT,
				PathCharRemover.RIGHT);
		this.directionComboBox.setValue(PathCharRemover.LEFT);
		this.fromSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, Integer.MAX_VALUE, 0));
		this.toSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, Integer.MAX_VALUE, 0));
	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}

	@FXML
	public void handleRemovingCharacter() {
		PathCharRemover pathCharRemover = new PathCharRemover();
		List<SelectedPath> newSelectedPathList = new ArrayList<>();

		this.selectedPathList.forEach(selectedPath -> {
			Path newPathName = pathCharRemover.remove(selectedPath.getBefore(),
					this.directionComboBox.getValue(),
					this.fromSpinner.getValue(), this.toSpinner.getValue());
			newSelectedPathList.add(new SelectedPath(selectedPath
					.getBeforeFullPath(), PathRenamer.extractNewPath(
					selectedPath, newPathName)));
		});
		this.selectedPathList.setAll(newSelectedPathList);
	}

}
