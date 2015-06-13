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

	private ObservableList<PairPath> pairPathList;

	private Stage stage;

	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.operationModeComboBox.getItems().setAll(
				PositionalRenaming.INSERTION_OPERATION,
				PositionalRenaming.OVERWRITEN_OPERATION);

		this.directionComboBox.getItems().setAll(PositionalRenaming.LEFT_SIDE,
				PositionalRenaming.RIGHT_SIDE);

		this.operationModeComboBox
				.setValue(PositionalRenaming.INSERTION_OPERATION);
		this.directionComboBox.setValue(PositionalRenaming.LEFT_SIDE);

		this.indexSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, Integer.MAX_VALUE, 0));

	}

	@FXML
	public void handleRenameOperation() {
		List<PairPath> newPairPathList = this.pathsRenamer
				.rename(this.pairPathList,
						new PositionalRenaming(this.operationModeComboBox
								.getValue(), this.directionComboBox.getValue(),
								this.indexSpinner.getValue(),
								this.newStringTextField.getText()));
		this.pairPathList.setAll(newPairPathList);
	}

	@FXML
	public void handleOkButton() {
		this.stage.close();
	}

	public void setPairPathList(ObservableList<PairPath> pairPathList) {
		this.pairPathList = pairPathList;

	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setPathsRenamer(PathsRenamer pathRenamer) {
		this.pathsRenamer = pathRenamer;
	}

}
