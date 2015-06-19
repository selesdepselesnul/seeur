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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
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
	private Button renamingButton;

	@FXML
	private Button clearingButton;

	@FXML
	private Button removingButton;

	@FXML
	private SplitMenuButton operationSplitMenuButton;

	private Stage stage;

	private ObservableList<PairPath> pairPathList;

	private PathsRenamer pathsRenamer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		disableControl(true);
		initColumn();
		this.pathsRenamer = new PathsRenamer();
		this.pairPathList = FXCollections.observableArrayList();
		this.pairPathTableView.setItems(pairPathList);
		this.pairPathTableView
				.setPlaceholder(new Label("No files are selected"));
		this.outputConsoleTextArea
				.setText("table is empty, click plus button for add files to table");

	}

	private void initColumn() {
		this.beforeTableColumn.setCellValueFactory(new PropertyValueFactory<>(
				"beforeFileName"));
		this.afterTableColumn.setCellValueFactory(new PropertyValueFactory<>(
				"afterFileName"));
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
		if (!this.pairPathList.isEmpty()) {
			this.outputConsoleTextArea.setStyle("-fx-text-fill: green");
		}
	}

	private void printErrorToConsoleOutput(Exception e) {
		this.outputConsoleTextArea.setStyle("-fx-text-fill: red");
		this.outputConsoleTextArea.setText("ouch... something going wrong !"
				+ "\n\n you rename files that don't exist :(");
	}

	@FXML
	public void handleSearchingAndReplacingMenuItem() {
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
			disableControl(false);
			this.outputConsoleTextArea.setStyle("-fx-text-fill: green");
			this.outputConsoleTextArea.setText("ready for renaming !\n");
			List<Path> pathList = fileList.stream()
					.map(files -> files.toPath()).collect(Collectors.toList());
			this.pairPathList.addAll(pathList.stream()
					.map(path -> new PairPath(path))
					.collect(Collectors.toList()));
		}
	}

	private void disableControl(boolean isDisable) {
		this.renamingButton.setDisable(isDisable);
		this.clearingButton.setDisable(isDisable);
		this.removingButton.setDisable(isDisable);
		this.operationSplitMenuButton.setDisable(isDisable);
	}

	@FXML
	public void handleClearButton() {

		disableControl(true);
		this.outputConsoleTextArea.setStyle("-fx-text-fill: white");
		this.pairPathList.clear();
		this.outputConsoleTextArea.clear();
		this.outputConsoleTextArea
				.setText("table is empty, click plus button for add files to table");
	}

	@FXML
	public void handleRenameButton() {
		if (!this.pairPathList.isEmpty()) {
			renameToDisk(this.pairPathList);
		}
	}

	private void renameToDisk(ObservableList<PairPath> pairPathList) {
		new Thread(
				() -> {
					List<PairPath> newPairPathList = new ArrayList<>();
					this.outputConsoleTextArea.setStyle("-fx-text-fill: green");
					this.outputConsoleTextArea.clear();
					this.pairPathList.forEach(pairPath -> {
						try {
							Path beforeFullPath = pairPath.getBeforeFullPath();
							Path afterFullPath = pairPath.getAfterFullPath();
							newPairPathList.add(new PairPath(afterFullPath,
									afterFullPath));
							Files.move(beforeFullPath, afterFullPath);
							outputConsoleTextArea.appendText("rename :\n"
									+ pairPath.getBeforeFullPath() + "\nto :\n"
									+ pairPath.getAfterFullPath() + "\n\n\n");
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}

					});
					this.pairPathList.setAll(new ArrayList<>(newPairPathList));
					this.outputConsoleTextArea
							.appendText("\n\n\nfinished renaming all files !\n\n\n");
				}).start();
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
			if (this.pairPathList.isEmpty()) {
				disableControl(true);
			}
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
	public void handleAboutButton() {
		try {

			this.outputConsoleTextArea.setStyle("-fx-text-fill: white");
			this.outputConsoleTextArea.clear();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							ClassLoader
									.getSystemResourceAsStream("seeurrenamer/main/resources/text/license.txt")));
			this.outputConsoleTextArea.setText(bufferedReader.lines().collect(
					Collectors.joining("\n")));
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
