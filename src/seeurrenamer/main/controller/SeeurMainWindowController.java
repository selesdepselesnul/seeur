package seeurrenamer.main.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SeeurMainWindowController implements Initializable {

	@FXML
	private TableView<PairPath> pairPathTableView;

	@FXML
	private TableColumn<PairPath, Path> beforeTableColumn;

	@FXML
	private TableColumn<PairPath, Path> afterTableColumn;

	@FXML
	private TextArea outputConsoleTextArea;

	@FXML
	private List<PairPath> oldPairPathList;

	private Stage stage;

	private ObservableList<PairPath> pairPathList;

	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.pathsRenamer = new PathsRenamer();
		this.oldPairPathList = new ArrayList<>();

		this.beforeTableColumn.setCellValueFactory(new PropertyValueFactory<>(
				"before"));
		this.afterTableColumn.setCellValueFactory(new PropertyValueFactory<>(
				"after"));

		this.pairPathList = FXCollections.observableArrayList();
		this.pairPathTableView.setItems(pairPathList);

		this.pairPathTableView
				.setPlaceholder(new Label("No files are selected"));

		this.outputConsoleTextArea
				.setText("table is empty, click plus button for add files to table");

	}

	@FXML
	public void handleOnInsAndOvMenuItem() {
		try {
			checkIfTableIsNotEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/InsertOrWriteManipulator.fxml",
					"insert / overwrite",
					(fxmlLoader, stage) -> {
						InsertOverwriteManipulatorController insertOverwriteManipulatorController = (InsertOverwriteManipulatorController) fxmlLoader
								.getController();
						insertOverwriteManipulatorController
								.setPairPathList(this.pairPathList);
						insertOverwriteManipulatorController.setStage(stage);
						insertOverwriteManipulatorController
								.setPathsRenamer(this.pathsRenamer);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	private void checkIfTableIsNotEmpty() {
		if (this.pairPathList.size() != 0) {
			this.outputConsoleTextArea.setStyle("-fx-text-fill: green");
		}
	}

	private void printErrorToConsoleOutput(Exception e) {
		this.outputConsoleTextArea.setStyle("-fx-text-fill: red");
		this.outputConsoleTextArea.setText("ouch... something going wrong !"
				+ "\n\n you rename files that don't exist :(");
	}

	@FXML
	public void handleSearchAndReplaceMenuItem() {
		try {
			checkIfTableIsNotEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/SearchAndReplaceManipulator.fxml",
					"seeurrenamer/main/resources/style/searching_and_replacing_window.css",
					"search and replace",
					(fxmlLoader, stage) -> {
						SearchingAndReplacingManipulatorController searchingAndReplacingManipulatorController = (SearchingAndReplacingManipulatorController) fxmlLoader
								.getController();
						searchingAndReplacingManipulatorController
								.setPairPathList(this.pairPathList);
						searchingAndReplacingManipulatorController
								.setPathsRenamer(this.pathsRenamer);
					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
		}

	}

	@FXML
	public void handleOnClickAddingButton() {

		FileChooser fileChooser = new FileChooser();
		List<File> fileList = fileChooser.showOpenMultipleDialog(this.stage);
		if (fileList != null) {
			this.outputConsoleTextArea.setStyle("-fx-text-fill: green");
			this.outputConsoleTextArea.setText("ready for renaming !\n");
			List<Path> pathList = fileList.stream()
					.map(files -> files.toPath()).collect(Collectors.toList());
			pairPathList.addAll(pathList.stream()
					.map(path -> new PairPath(path))
					.collect(Collectors.toList()));

			this.pairPathList.forEach(selectedPath -> this.oldPairPathList
					.add(selectedPath));
		}
	}

	@FXML
	public void handleRenameButton() {

		if (this.pairPathList.size() != 0) {
			this.outputConsoleTextArea.setStyle("-fx-text-fill: green");
			List<PairPath> newPairPathList = new ArrayList<>();
			this.outputConsoleTextArea.clear();

			this.pairPathList.forEach(pairPath -> {
				try {
					Path beforeFullPath = pairPath.getBeforeFullPath();
					Path afterFullPath = pairPath.getAfterFullPath();
					newPairPathList.add(new PairPath(afterFullPath,
							afterFullPath));
					Files.move(beforeFullPath, afterFullPath);
					this.outputConsoleTextArea.appendText("rename : "
							+ pairPath.getBeforeFullPath() + "\nto : "
							+ pairPath.getAfterFullPath() + "\n\n");
				} catch (Exception e) {
					printErrorToConsoleOutput(e);
					e.printStackTrace();

				}
			});
			this.pairPathList.setAll(newPairPathList);
		}

	}

	@FXML
	public void handleClearButton() {

		this.outputConsoleTextArea.setStyle("-fx-text-fill: white");

		this.pairPathList.clear();
		this.outputConsoleTextArea.clear();
		this.outputConsoleTextArea
				.setText("table is empty, click plus button for add files to table");
	}

	@FXML
	public void handleDeleteButton() {
		PairPath pairPath = this.pairPathTableView.getSelectionModel()
				.getSelectedItem();
		if (pairPath != null) {
			this.outputConsoleTextArea.setStyle("-fx-text-fill: green");
			this.outputConsoleTextArea.appendText("delete from table : "
					+ pairPath.toString() + "\n\n");
			this.pairPathList.remove(pairPath);
		}
	}

	@FXML
	public void handleCaseConverterMenuItem() {
		try {
			checkIfTableIsNotEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/CaseManipulator.fxml",
					"convert case",
					(fxmlLoader, stage) -> {
						CaseConverterController caseConverterController = (CaseConverterController) fxmlLoader
								.getController();
						caseConverterController
								.setPairPathList(this.pairPathList);
						caseConverterController
								.setPathsRenamer(this.pathsRenamer);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	@FXML
	public void handleNumberingFormatMenuItem() {
		try {
			checkIfTableIsNotEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/SequenceManipulator.fxml",
					"give sequence",
					(fxmlLoader, stage) -> {
						SequenceManipulatorController sequenceManipulatorController = (SequenceManipulatorController) fxmlLoader
								.getController();
						sequenceManipulatorController
								.setPairPathList(this.pairPathList);
						sequenceManipulatorController
								.setPathsRename(this.pathsRenamer);
					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	@FXML
	public void handleRemovingCharacterMenuItem() {
		try {
			checkIfTableIsNotEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/RemovingCharManipulator.fxml",
					"remove character",
					(fxmlLoader, stage) -> {
						RemovingCharManipulatorController removingCharManipulatorController = (RemovingCharManipulatorController) fxmlLoader
								.getController();
						removingCharManipulatorController
								.setPairPathList(this.pairPathList);
						removingCharManipulatorController
								.setPathsRenamer(this.pathsRenamer);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	@FXML
	public void handleBackButton() {
		this.pairPathList.clear();
		this.oldPairPathList.forEach(pairPath -> this.pairPathList
				.add(new PairPath(pairPath.getBeforeFullPath(),
						pairPath.getAfterFullPath())));
	}

	@FXML
	public void handleAboutButton() {

		this.outputConsoleTextArea.setStyle("-fx-text-fill: white");
		this.outputConsoleTextArea.clear();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(
						ClassLoader
								.getSystemResourceAsStream("seeurrenamer/main/resources/text/license.txt")));
		bufferedReader.lines().forEach(
				line -> this.outputConsoleTextArea.appendText(line + "\n"));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
