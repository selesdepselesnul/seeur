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

import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SeeurRenamerMainWindowController implements Initializable {

	@FXML
	private TableView<SelectedPath> selectedPathTableView;

	@FXML
	private TableColumn<SelectedPath, Path> beforeTableColumn;

	@FXML
	private TableColumn<SelectedPath, Path> afterTableColumn;

	@FXML
	private TitledPane manipulatorTitledPane;

	@FXML
	private GridPane insertOrOverwritePane;

	@FXML
	private TextArea outputTextArea;

	@FXML
	private List<SelectedPath> oldSelectedList;

	private Stage stage;

	private ObservableList<SelectedPath> selectedPathList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.oldSelectedList = new ArrayList<>();
		this.beforeTableColumn.setCellValueFactory(new PropertyValueFactory<>(
				"before"));
		this.afterTableColumn.setCellValueFactory(new PropertyValueFactory<>(
				"after"));
		this.selectedPathList = FXCollections.observableArrayList();
		this.selectedPathTableView.setItems(selectedPathList);
		this.selectedPathTableView.setPlaceholder(new Label(
				"No files are selected"));
		this.outputTextArea
				.setText("table is empty, click plus button for add files to table");

	}

	@FXML
	public void handleOnInsAndOvMenuItem() {
		try {
			checkIfTableEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/InsertOrWriteManipulator.fxml",
					"insert / overwrite",
					(fxmlLoader, stage) -> {
						InsertOverwriteManipulatorController insertOverwriteManipulatorController = (InsertOverwriteManipulatorController) fxmlLoader
								.getController();
						insertOverwriteManipulatorController
								.setSelectedPathList(this.selectedPathList);
						insertOverwriteManipulatorController.setStage(stage);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	private void checkIfTableEmpty() {
		if (this.selectedPathList.size() != 0) {
			this.outputTextArea.setStyle("-fx-text-fill: green");
		}
	}

	private void printErrorToConsoleOutput(Exception e) {
		this.outputTextArea.setStyle("-fx-text-fill: red");
		this.outputTextArea
				.setText("ouch... something going wrong !\n\n you rename files that don't exist :(");
	}

	@FXML
	public void handleSearchAndReplaceMenuItem() {
		try {
			checkIfTableEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/SearchAndReplaceManipulator.fxml",
					"seeurrenamer/main/resources/style/searching_and_replacing_window.css",
					"search and replace",
					(fxmlLoader, stage) -> {
						SearchingAndReplacingManipulatorController searchingAndReplacingManipulatorController = (SearchingAndReplacingManipulatorController) fxmlLoader
								.getController();
						searchingAndReplacingManipulatorController
								.setSelectedPathList(this.selectedPathList);

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
			this.outputTextArea.setStyle("-fx-text-fill: green");
			this.outputTextArea.setText("ready for renaming !\n");
			List<Path> pathList = fileList.stream()
					.map(files -> files.toPath()).collect(Collectors.toList());
			selectedPathList.addAll(pathList.stream()
					.map(path -> new SelectedPath(path))
					.collect(Collectors.toList()));

			this.selectedPathList.forEach(selectedPath -> this.oldSelectedList
					.add(selectedPath));
		}
	}

	@FXML
	public void handleRenameButton() {

		if (this.selectedPathList.size() != 0) {
			this.outputTextArea.setStyle("-fx-text-fill: green");
			List<SelectedPath> newSelectedPathList = new ArrayList<>();
			this.outputTextArea.clear();

			this.selectedPathList.forEach(selectedPath -> {
				try {
					Path beforeFullPath = selectedPath.getBeforeFullPath();
					Path afterFullPath = selectedPath.getAfterFullPath();
					newSelectedPathList.add(new SelectedPath(afterFullPath,
							afterFullPath));
					Files.move(beforeFullPath, afterFullPath);
					this.outputTextArea.appendText("rename : "
							+ selectedPath.getBeforeFullPath() + "\nto : "
							+ selectedPath.getAfterFullPath() + "\n\n");
				} catch (Exception e) {
					printErrorToConsoleOutput(e);
					e.printStackTrace();

				}
			});
			this.selectedPathList.setAll(newSelectedPathList);
		}

	}

	@FXML
	public void handleClearButton() {
		this.outputTextArea.setStyle("-fx-text-fill: white");

		this.selectedPathList.clear();
		this.outputTextArea.clear();
		this.outputTextArea
				.setText("table is empty, click plus button for add files to table");
	}

	@FXML
	public void handleDeleteButton() {
		SelectedPath selectedPath = this.selectedPathTableView
				.getSelectionModel().getSelectedItem();
		if (selectedPath != null) {
			this.outputTextArea.setStyle("-fx-text-fill: green");
			this.outputTextArea.appendText("delete from table : "
					+ selectedPath.toString() + "\n\n");
			this.selectedPathList.remove(selectedPath);
		}
	}

	@FXML
	public void handleCaseConverterMenuItem() {
		try {
			checkIfTableEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/CaseManipulator.fxml",
					"convert case",
					(fxmlLoader, stage) -> {
						CaseConverterController caseConverterController = (CaseConverterController) fxmlLoader
								.getController();
						caseConverterController
								.setSelectedPathList(this.selectedPathList);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	@FXML
	public void handleNumberingFormatMenuItem() {
		try {
			checkIfTableEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/SequenceManipulator.fxml",
					"give sequence",
					(fxmlLoader, stage) -> {
						SequenceManipulatorController sequenceManipulatorController = (SequenceManipulatorController) fxmlLoader
								.getController();
						sequenceManipulatorController
								.setSelectedPathList(this.selectedPathList);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	@FXML
	public void handleRemovingCharacterMenuItem() {
		try {
			checkIfTableEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/RemovingCharManipulator.fxml",
					"remove character",
					(fxmlLoader, stage) -> {
						RemovingCharManipulatorController removingCharManipulatorController = (RemovingCharManipulatorController) fxmlLoader
								.getController();
						removingCharManipulatorController
								.setSelectedPathList(this.selectedPathList);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}

	}

	@FXML
	public void handleBackButton() {
		this.selectedPathList.clear();
		this.oldSelectedList.forEach(selectedPath -> this.selectedPathList
				.add(new SelectedPath(selectedPath.getBeforeFullPath(),
						selectedPath.getAfterFullPath())));
	}

	@FXML
	public void handleAboutButton() {

		this.outputTextArea.setStyle("-fx-text-fill: white");
		this.outputTextArea.clear();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(
						ClassLoader
								.getSystemResourceAsStream("seeurrenamer/main/resources/text/license.txt")));
		bufferedReader.lines().forEach(
				line -> this.outputTextArea.appendText(line + "\n"));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
