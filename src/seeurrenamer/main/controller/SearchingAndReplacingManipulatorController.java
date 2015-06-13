package seeurrenamer.main.controller;

import java.io.IOException;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.RegexRenaming;
import seeurrenamer.main.util.WindowLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SearchingAndReplacingManipulatorController {

	@FXML
	private TextField regexTextField;

	@FXML
	private TextField replacerTextField;

	@FXML
	private CheckBox isCaseInsensitiveCheckBox;

	private ObservableList<PairPath> pairPathList;

	private PathsRenamer pathsRenamer;

	@FXML
	public void handleOnWriteString() {
		RegexRenaming regexRenaming = new RegexRenaming(
				this.regexTextField.getText(),
				this.replacerTextField.getText(),
				this.isCaseInsensitiveCheckBox.isSelected());
		this.pairPathList.setAll(this.pathsRenamer.rename(
				this.pairPathList, regexRenaming));

	}

	public void setPairPathList(ObservableList<PairPath> pairPathList) {
		this.pairPathList = pairPathList;
	}

	@FXML
	public void handleHelpRegex() {
		try {
			new WindowLoader("seeurrenamer/main/view/RegexSummaryWindow.fxml",
					"Regex Summary", null).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPathsRenamer(PathsRenamer pathsRenamer) {
		this.pathsRenamer = pathsRenamer;
	}
}
