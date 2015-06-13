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
		// List<SelectedPath> newSelectedPathList = new ArrayList<>();
		// if (this.sequenceComboBox.getValue().equals("1., 2., 3. ...")) {
		// for (int i = 0; i < this.selectedPathList.size(); i++) {
		// SelectedPath selectedPath = this.selectedPathList.get(i);
		// Path newPathName = (Paths.get((i + 1) + "."
		// + selectedPath.getBefore()));
		//
		// newSelectedPathList.add(new SelectedPath(selectedPath
		// .getBeforeFullPath(), PathRenamer.extractNewPath(
		// selectedPath, newPathName)));
		// }
		// } else if (this.sequenceComboBox.getValue()
		// .equals("a., b., c., d. ...")) {
		// char charNumber = 'a';
		// for (SelectedPath selectedPath : this.selectedPathList) {
		// Path newPathName = Paths.get(charNumber + "."
		// + selectedPath.getBefore());
		//
		// newSelectedPathList.add(new SelectedPath(selectedPath
		// .getBeforeFullPath(), PathRenamer.extractNewPath(
		// selectedPath, newPathName)));
		// if (charNumber == 'z') {
		// charNumber = 'a';
		// }
		// charNumber++;
		// }
		// }
		// this.selectedPathList.setAll(newSelectedPathList);
	}

	public void setSelectedPathList(
			ObservableList<SelectedPath> selectedPathList) {
		this.selectedPathList = selectedPathList;
	}
}
