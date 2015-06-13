package seeurrenamer.main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.model.Sequence;
import seeurrenamer.main.util.SequenceAdder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class SequenceManipulatorController implements Initializable {

	@FXML
	private ComboBox<Sequence<?>> sequenceComboBox;
	private ObservableList<SelectedPath> selectedPathList;
	private SequenceAdder sequencerAdder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.sequenceComboBox.getItems().addAll(
				new Sequence<Integer>(1, Integer.MAX_VALUE,
						integer -> integer + 1, "1., 2., 3., .."),
				new Sequence<Character>('a', 'z', character -> ++character,
						"a., b., c., ..."));
	}

	@FXML
	public void handleNumberingFormatComboBox() {
		sequencerAdder = new SequenceAdder(this.selectedPathList);
		this.selectedPathList.setAll(sequencerAdder.add(this.sequenceComboBox
				.getValue()));
	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}
}
