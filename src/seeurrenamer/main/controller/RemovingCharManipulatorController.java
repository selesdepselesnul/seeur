package seeurrenamer.main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.RemoverRenaming;
import seeurrenamer.main.util.PathsRenamer;
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

	private ObservableList<PairPath> pairPathList;

	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.directionComboBox.getItems().setAll(RemoverRenaming.LEFT,
				RemoverRenaming.RIGHT);
		this.directionComboBox.setValue(RemoverRenaming.LEFT);
		this.fromSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, Integer.MAX_VALUE, 0));
		this.toSpinner
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
						0, Integer.MAX_VALUE, 0));
	}

	public void setPairPathList(ObservableList<PairPath> pairPathList) {
		this.pairPathList = pairPathList;
	}

	@FXML
	public void handleRemovingCharacter() {
		RemoverRenaming removeRenaming = new RemoverRenaming(
				this.directionComboBox.getValue(), this.fromSpinner.getValue(),
				this.toSpinner.getValue());
		this.pairPathList.setAll(this.pathsRenamer.rename(
				this.pairPathList, removeRenaming));
	}

	public void setPathsRenamer(PathsRenamer pathsRenamer) {
		this.pathsRenamer = pathsRenamer;
	}

}
