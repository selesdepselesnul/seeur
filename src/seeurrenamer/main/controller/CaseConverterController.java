package seeurrenamer.main.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.caseconverter.LowerConverter;
import seeurrenamer.main.util.caseconverter.PathCaseConverter;
import seeurrenamer.main.util.caseconverter.UnixStyleConverter;
import seeurrenamer.main.util.caseconverter.UpperConverter;

public class CaseConverterController implements Initializable {

	@FXML
	private ComboBox<Function<String, String>> caseComboBox;

	private ObservableList<SelectedPath> selectedPathList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.caseComboBox.getItems().addAll(
				Arrays.asList(new UpperConverter(), new LowerConverter(),
						new UnixStyleConverter()));
	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}

	@FXML
	public void handleCaseComboBox() {
		PathCaseConverter pathCaseConverter = new PathCaseConverter(
				this.selectedPathList);
		this.selectedPathList.setAll(pathCaseConverter
				.convertCase(this.caseComboBox.getValue()));
	}
}
