package seeurrenamer.main.controller.manipulator;

import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Function;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.sequence.AlphabetSequenceRenamer;
import seeurrenamer.main.util.sequence.DecimalSequenceRenamer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * @author moch deden 
 *
 */
public class SequenceManipulatorController implements Initializable {

	@FXML
	private ComboBox<Function<Path, Path>> sequenceComboBox;
	private ObservableList<PairPath> pairPathList;
	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.sequenceComboBox.getItems().addAll(
				Arrays.asList(new DecimalSequenceRenamer(),
						new AlphabetSequenceRenamer()));
	}

	@FXML
	public void handleNumberingFormatComboBox() {
		this.pairPathList.setAll(this.pathsRenamer.rename(
				this.pairPathList, this.sequenceComboBox.getValue()));
	}

	public void setPairPathList(ObservableList<PairPath> pairPathList) {
		this.pairPathList = pairPathList;
	}

	public void setPathsRename(PathsRenamer pathsRenamer) {
		this.pathsRenamer = pathsRenamer;
	}
}
