package seeurrenamer.main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import seeurrenamer.main.model.RenameMethod;
import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.PathRenamer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class InsertOverwriteManipulatorController implements Initializable {
	@FXML
	private ComboBox<String> operationModeComboBox;

	@FXML
	private ComboBox<String> directionComboBox;

	@FXML
	private TextField newStringTextField;

	@FXML
	private Spinner<Integer> indexSpinner;

	private ObservableList<SelectedPath> selectedPathList;

	private PathRenamer pathRenamer;

	private RenameMethod renameMethod;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.operationModeComboBox.getItems().setAll(
				PathRenamer.INSERT_OPERATION, PathRenamer.OVERWRITE_OPERATION);
		this.operationModeComboBox.setValue(PathRenamer.INSERT_OPERATION);
		this.directionComboBox.getItems().setAll(PathRenamer.LEFT_SIDE,
				PathRenamer.RIGHT_SIDE);
		this.directionComboBox.setValue(PathRenamer.LEFT_SIDE);
		this.indexSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, 3000, 0));
		this.pathRenamer = new PathRenamer();
	}

	@FXML
	public void handleRenameOperation() {
		this.selectedPathList.setAll(this.pathRenamer.rename(this.selectedPathList,
				this.indexSpinner.getValue(),
				this.newStringTextField.getText(),
				this.directionComboBox.getValue(),
				this.operationModeComboBox.getValue()));
		System.out.println(this.newStringTextField.getText());
	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}

	public void setRenameMethod(RenameMethod renameMethod) {
		this.renameMethod = renameMethod;
	}
}
