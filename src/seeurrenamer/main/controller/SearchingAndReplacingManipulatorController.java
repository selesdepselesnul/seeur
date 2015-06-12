package seeurrenamer.main.controller;

import java.io.IOException;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.RegexPathRenamer;
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

	private ObservableList<SelectedPath> selectedPathList;

	private RegexPathRenamer regexPathRenamer;

	@FXML
	public void handleOnWriteString() {
		if (this.isCaseInsensitiveCheckBox.isSelected()) {
			this.regexPathRenamer = new RegexPathRenamer(
					this.regexTextField.getText(), true);
		} else {
			this.regexPathRenamer = new RegexPathRenamer(
					this.regexTextField.getText());
		}
		this.selectedPathList.setAll(regexPathRenamer.rename(
				this.selectedPathList, this.replacerTextField.getText()));

	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
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
}
