package seeurrenamer.main.controller;

import java.io.IOException;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.RegexRenamer;
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
		RegexRenamer regexRenaming = new RegexRenamer(
				this.regexTextField.getText(),
				this.replacerTextField.getText(),
				this.isCaseInsensitiveCheckBox.isSelected());
		this.pairPathList.setAll(this.pathsRenamer.rename(this.pairPathList,
				regexRenaming));

	}

	public void setPairPathList(ObservableList<PairPath> pairPathList) {
		this.pairPathList = pairPathList;
	}

	@FXML
	public void handleHelpRegex() {
		try {
			WindowLoader windowLoader = new WindowLoader(
					"seeurrenamer/main/view/RegexSummaryWindow.fxml",
					"Regex Summary", null);
			windowLoader.setResizable(true);
			windowLoader.show(WindowLoader.SHOW_ONLY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPathsRenamer(PathsRenamer pathsRenamer) {
		this.pathsRenamer = pathsRenamer;
	}
}
