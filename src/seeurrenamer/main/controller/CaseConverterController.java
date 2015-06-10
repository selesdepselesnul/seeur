package seeurrenamer.main.controller;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringJoiner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.PathRenamer;

public class CaseConverterController implements Initializable {

	@FXML
	private ComboBox<String> caseComboBox;

	private ObservableList<SelectedPath> selectedPathList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.caseComboBox.getItems().addAll("UPPERCASE", "lowercase",
				"UNIX-Style");
	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}

	@FXML
	public void handleCaseComboBox() {
		List<SelectedPath> newSelectedPathList = new ArrayList<>();
		if (this.caseComboBox.getSelectionModel().getSelectedItem()
				.equals("UPPERCASE")) {
			System.out.println("selected uppercase");
			this.selectedPathList.stream().forEach(
					selectedPath -> {
						Path lowerCasePath = Paths.get(selectedPath.getBefore()
								.toString().toUpperCase());
						newSelectedPathList.add(new SelectedPath(selectedPath
								.getBeforeFullPath(), PathRenamer
								.extractNewPath(selectedPath, lowerCasePath)));
					});
		} else if (this.caseComboBox.getSelectionModel().getSelectedItem()
				.equals("lowercase")) {
			this.selectedPathList.stream().forEach(
					selectedPath -> {
						Path lowerCasePath = Paths.get(selectedPath.getBefore()
								.toString().toLowerCase());
						newSelectedPathList.add(new SelectedPath(selectedPath
								.getBeforeFullPath(), PathRenamer
								.extractNewPath(selectedPath, lowerCasePath)));
					});

		} else if (this.caseComboBox.getSelectionModel().getSelectedItem()
				.equals("UNIX-Style")) {
			this.selectedPathList.stream().forEach(
					selectedPath -> {
						Path unixStylePath = Paths.get(selectedPath.getBefore()
								.toString().toLowerCase()
								.replaceAll("\\s+|\\p{Punct}", "_"));
						unixStylePath = Paths.get(unixStylePath.toString()
								.replaceFirst("_{2,}", "_"));
						unixStylePath = Paths.get(unixStylePath.toString()
								.replaceAll("_+$", ""));
						newSelectedPathList.add(new SelectedPath(selectedPath
								.getBeforeFullPath(), PathRenamer
								.extractNewPath(selectedPath, unixStylePath)));

					});

		}
		this.selectedPathList.setAll(newSelectedPathList);
	}
}
