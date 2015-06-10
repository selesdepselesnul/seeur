package seeurrenamer.main.controller;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.PathRenamer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class NumberingManipulatorController implements Initializable {

	@FXML
	private ComboBox<String> numberingFormatComboBox;
	private ObservableList<SelectedPath> selectedPathList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.numberingFormatComboBox.getItems().addAll("1., 2., 3. ...",
				"a., b., c., d. ...");
	}

	@FXML
	public void handleNumberingFormatComboBox() {
		List<SelectedPath> newSelectedPathList = new ArrayList<>();
		if (this.numberingFormatComboBox.getValue().equals("1., 2., 3. ...")) {
			for (int i = 0; i < this.selectedPathList.size(); i++) {
				SelectedPath selectedPath = this.selectedPathList.get(i);
				Path newPathName = (Paths.get((i + 1) + "."
						+ selectedPath.getBefore()));
				System.out.println(newPathName);
				newSelectedPathList.add(new SelectedPath(selectedPath
						.getBeforeFullPath(), PathRenamer.extractNewPath(
						selectedPath, newPathName)));
			}
		} else if (this.numberingFormatComboBox.getValue().equals(
				"a., b., c., d. ...")) {
			char charNumber = 'a';
			for (SelectedPath selectedPath : this.selectedPathList) {
				System.out.println("char numbering system");
				Path newPathName = Paths.get(charNumber + "."
						+ selectedPath.getAfter().toString());
				System.out.println(newPathName);
				newSelectedPathList.add(new SelectedPath(selectedPath
						.getBeforeFullPath(), PathRenamer.extractNewPath(
						selectedPath, newPathName)));
				if (charNumber == 'z') {
					charNumber = 'a';
				} else {
					charNumber++;
				}
			}
		}
		this.selectedPathList.setAll(newSelectedPathList);
	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}
}
