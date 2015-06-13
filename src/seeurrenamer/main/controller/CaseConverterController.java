package seeurrenamer.main.controller;

import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.caseconverter.LowerConverter;
import seeurrenamer.main.util.caseconverter.UnixStyleConverter;
import seeurrenamer.main.util.caseconverter.UpperConverter;

public class CaseConverterController implements Initializable {

	@FXML
	private ComboBox<Function<Path, Path>> caseComboBox;

	private ObservableList<PairPath> pairPathList;

	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.caseComboBox.getItems().addAll(
				Arrays.asList(new UpperConverter(), new LowerConverter(),
						new UnixStyleConverter()));
	}

	public void setSelectedPathList(ObservableList<PairPath> selectedPathList) {
		this.pairPathList = selectedPathList;
	}

	@FXML
	public void handleCaseComboBox() {
		this.pairPathList.setAll(this.pathsRenamer.rename(
				this.pairPathList, this.caseComboBox.getValue()));
	}

	public void setPathsRenamer(PathsRenamer pathsRenamer) {
		this.pathsRenamer = pathsRenamer;
	}
}
