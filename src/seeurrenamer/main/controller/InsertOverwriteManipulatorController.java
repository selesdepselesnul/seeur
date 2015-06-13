package seeurrenamer.main.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.PositionalRenaming;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InsertOverwriteManipulatorController implements Initializable {
	@FXML
	private ComboBox<String> operationModeComboBox;

	@FXML
	private ComboBox<String> directionComboBox;

	@FXML
	private TextField newStringTextField;

	@FXML
	private Spinner<Integer> indexSpinner;

	private ObservableList<PairPath> selectedPathList;

	private Stage stage;

	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.operationModeComboBox.getItems().setAll(
				PositionalRenaming.INSERT_OPERATION,
				PositionalRenaming.OVERWRITE_OPERATION);

		this.directionComboBox.getItems().setAll(PositionalRenaming.LEFT_SIDE,
				PositionalRenaming.RIGHT_SIDE);

		this.operationModeComboBox
				.setValue(PositionalRenaming.INSERT_OPERATION);

		this.directionComboBox.setValue(PositionalRenaming.LEFT_SIDE);

		this.indexSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, Integer.MAX_VALUE, 0));

	}

	@FXML
	public void handleRenameOperation() {
		List<PairPath> newPairPathList = this.pathsRenamer
				.rename(this.selectedPathList,
						new PositionalRenaming(this.operationModeComboBox
								.getValue(), this.directionComboBox.getValue(),
								this.indexSpinner.getValue(),
								this.newStringTextField.getText()));
		this.selectedPathList.setAll(newPairPathList);
	}

	@FXML
	public void handleOkButton() {
		this.stage.close();
	}

	public void setSelectedPathList(ObservableList<PairPath> selectedPathList) {
		this.selectedPathList = selectedPathList;

	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setPathsRenamer(PathsRenamer pathRenamer) {
		this.pathsRenamer = pathRenamer;
	}

}
