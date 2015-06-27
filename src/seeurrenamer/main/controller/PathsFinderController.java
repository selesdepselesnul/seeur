package seeurrenamer.main.controller;

import java.io.IOException;
import java.util.List;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsFinder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class PathsFinderController {

	@FXML
	private TextField regexTextField;
	@FXML
	private ToggleButton regexOnOffToggleButton;
	private ObservableList<PairPath> pairPathList;
	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setPairPathList(ObservableList<PairPath> pairPathList) {
		this.pairPathList = pairPathList;
	}

	@FXML
	public void handleSearchingButton() {
		try {
			List<PairPath> filteredPairPathList = null;
			PathsFinder pathsFinder = new PathsFinder(this.stage);
			if (this.regexOnOffToggleButton.isSelected()) {
				filteredPairPathList = pathsFinder.search(this.regexTextField
						.getText());
			} else {
				filteredPairPathList = pathsFinder.search();
			}
			this.pairPathList.addAll(filteredPairPathList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleOnOffToggleButton() {
		if (this.regexOnOffToggleButton.isSelected()) {
			this.regexTextField.setDisable(false);
			this.regexOnOffToggleButton
					.setStyle("-fx-background-image: url('seeurrenamer/main/resources/style/image/bullet_green.png')");
		} else {
			this.regexTextField.setDisable(true);
			this.regexOnOffToggleButton
					.setStyle("-fx-background-image: url('seeurrenamer/main/resources/style/image/bullet_red.png')");
		}
		this.regexTextField.clear();
	}
}
