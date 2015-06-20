package seeurrenamer.main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.RenamingDirection;
import seeurrenamer.main.util.positionalrenaming.InsertionPositionalRenamer;
import seeurrenamer.main.util.positionalrenaming.OverwritingPositionalRenamer;
import seeurrenamer.main.util.positionalrenaming.PositionalRenaming;
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
	private ComboBox<PositionalRenaming> positionalRenamingComboBox;

	@FXML
	private ComboBox<RenamingDirection> positionalRenamingDirectionComboBox;

	@FXML
	private TextField newStringTextField;

	@FXML
	private Spinner<Integer> indexSpinner;

	private ObservableList<PairPath> pairPathList;

	private Stage stage;

	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.positionalRenamingComboBox.getItems().setAll(
				new InsertionPositionalRenamer(),
				new OverwritingPositionalRenamer());

		this.positionalRenamingDirectionComboBox.getItems().setAll(RenamingDirection.LEFT,
				RenamingDirection.RIGHT);

		this.positionalRenamingComboBox
				.setValue(new InsertionPositionalRenamer());
		this.positionalRenamingDirectionComboBox.setValue(RenamingDirection.LEFT);

		this.indexSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, Integer.MAX_VALUE, 0));

	}

	@FXML
	public void handleRenameOperation() {
		PositionalRenaming positionalRenaming = this.positionalRenamingComboBox
				.getValue();
		positionalRenaming.setDirection(this.positionalRenamingDirectionComboBox.getValue());
		positionalRenaming.setPosition(this.indexSpinner.getValue());
		positionalRenaming.setNewString(this.newStringTextField.getText());
		this.pairPathList.setAll(this.pathsRenamer.rename(this.pairPathList,
				positionalRenaming));
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
