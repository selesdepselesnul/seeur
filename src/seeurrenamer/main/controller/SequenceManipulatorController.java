package seeurrenamer.main.controller;

import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Function;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.sequence.AlphabetSequenceRenaming;
import seeurrenamer.main.util.sequence.DecimalSequenceRenaming;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class SequenceManipulatorController implements Initializable {

	@FXML
	private ComboBox<Function<Path, Path>> sequenceComboBox;
	private ObservableList<PairPath> selectedPathList;
	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.sequenceComboBox.getItems().addAll(
				Arrays.asList(new DecimalSequenceRenaming(),
						new AlphabetSequenceRenaming()));
	}

	@FXML
	public void handleNumberingFormatComboBox() {
		this.selectedPathList.setAll(this.pathsRenamer.rename(
				this.selectedPathList, this.sequenceComboBox.getValue()));
	}

	public void setSelectedPathList(ObservableList<PairPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}

	public void setPathsRename(PathsRenamer pathsRenamer) {
		this.pathsRenamer = pathsRenamer;
	}
}
