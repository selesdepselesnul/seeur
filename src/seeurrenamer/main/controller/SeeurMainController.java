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
import java.util.stream.Stream;

import seeurrenamer.main.controller.manipulator.CaseManipulatorController;
import seeurrenamer.main.controller.manipulator.InsertingOrOverwritingManipulatorController;
import seeurrenamer.main.controller.manipulator.RemoverManipulatorController;
import seeurrenamer.main.controller.manipulator.SearchingAndReplacingManipulatorController;
import seeurrenamer.main.controller.manipulator.SequenceManipulatorController;
import seeurrenamer.main.model.PairPath;
import seeurrenamer.main.util.PathsRenamer;
import seeurrenamer.main.util.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

/**
 * @author moch deden
 *
 */
public class SeeurMainController implements Initializable {

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
					"seeurrenamer/main/view/manipulator/InsertingOrOverwritingManipulator.fxml",
					"insert / overwrite",
					(fxmlLoader, stage) -> {
						InsertingOrOverwritingManipulatorController insAndOvwManipulatorController = (InsertingOrOverwritingManipulatorController) fxmlLoader
								.getController();
						insAndOvwManipulatorController
								.setPairPathList(this.pairPathList);
						insAndOvwManipulatorController.setStage(stage);
						insAndOvwManipulatorController
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
		this.outputConsoleTextArea.setText("ouch... something going wrong !");
	}

	@FXML
	public void handleSearchingAndReplacingMenuItem() {
		try {
			checkIfTableIsNotEmpty();
			new WindowLoader(
					"seeurrenamer/main/view/manipulator/SearchingAndReplacingManipulator.fxml",
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
			addFileStreamToTable(fileList.stream().map(files -> files.toPath())
					.collect(Collectors.toList()).stream());
		}
	}

	private void addFileStreamToTable(Stream<Path> pathStream) {
		this.pairPathList.addAll(pathStream.map(path -> new PairPath(path))
				.collect(Collectors.toList()));
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
					"seeurrenamer/main/view/manipulator/CaseManipulator.fxml",
					"convert case",
					(fxmlLoader, stage) -> {
						CaseManipulatorController caseManipulatorController = (CaseManipulatorController) fxmlLoader
								.getController();
						caseManipulatorController
								.setPairPathList(this.pairPathList);
						caseManipulatorController
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
					"seeurrenamer/main/view/manipulator/SequenceManipulator.fxml",
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
					"seeurrenamer/main/view/manipulator/RemoverManipulator.fxml",
					"remove character",
					(fxmlLoader, stage) -> {
						RemoverManipulatorController removerManipulatorController = (RemoverManipulatorController) fxmlLoader
								.getController();
						removerManipulatorController
								.setPairPathList(this.pairPathList);
						removerManipulatorController
								.setPathsRenamer(this.pathsRenamer);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			printErrorToConsoleOutput(e);
			e.printStackTrace();
		}
	}

	@FXML
	public void handleAboutButton(ActionEvent actionEvent) {
		try {

			Button button = (Button) actionEvent.getSource();
			String resources = null;
			if (button.getId().equals("system-help-button")) {
				resources = "seeurrenamer/main/resources/text/regex_summary.txt";
			} else {
				System.out.println("about");
				resources = "seeurrenamer/main/resources/text/license.txt";
			}
			this.outputConsoleTextArea.setStyle("-fx-text-fill: white");
			this.outputConsoleTextArea.clear();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							ClassLoader.getSystemResourceAsStream(resources)));
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

	@FXML
	public void handleSearchingPathsButton() {
		try {
			new WindowLoader(
					"seeurrenamer/main/view/PathsFinder.fxml",
					"seeurrenamer/main/resources/style/paths_finder.css",
					"Paths Searcher",
					(fxmlLoader, stage) -> {
						PathsFinderController pathsFinderController = (PathsFinderController) fxmlLoader
								.getController();
						pathsFinderController.setStage(stage);
						pathsFinderController
								.setPairPathList(this.pairPathList);
					}).show(WindowLoader.SHOW_AND_WAITING);
			if (!this.pairPathList.isEmpty()) {
				disableControl(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
