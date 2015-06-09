package seeurrenamer.main.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import seeurrenamer.main.model.RenameMethod;
import seeurrenamer.main.model.SelectedPath;
import seeurrenamer.main.util.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
	private ComboBox<RenameMethod> renameMethodComboBox;

	@FXML
	private TitledPane manipulatorTitledPane;

	@FXML
	private GridPane insertOrOverwritePane;

	@FXML
	private TextArea outputTextArea;

	private Stage stage;

	private ObservableList<SelectedPath> selectedPathList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
			this.beforeTableColumn
					.setCellValueFactory(new PropertyValueFactory<>("before"));
			this.afterTableColumn
					.setCellValueFactory(new PropertyValueFactory<>("after"));
			this.selectedPathList = FXCollections.observableArrayList();
			this.selectedPathTableView.setItems(selectedPathList);
			this.renameMethodComboBox.getItems().setAll(
					RenameMethod.NAME_AND_SUFFIX, RenameMethod.NAME,
					RenameMethod.SUFFIX);
			this.renameMethodComboBox.setValue(RenameMethod.NAME_AND_SUFFIX);
		
		}

	@FXML
	public void handleOnInsAndOvMenuItem() {
		 try {
			new WindowLoader(
					"seeurrenamer/main/view/InsertOrWriteManipulator.fxml",
					"manipulator",
					(fxmlLoader, stage) -> {
						InsertOverwriteManipulatorController insertOverwriteManipulatorController = (InsertOverwriteManipulatorController) fxmlLoader
								.getController();
						insertOverwriteManipulatorController
								.setSelectedPathList(this.selectedPathList);
						insertOverwriteManipulatorController
								.setRenameMethod(this.renameMethodComboBox
										.getSelectionModel().getSelectedItem());
						insertOverwriteManipulatorController.setStage(stage);

					}).show(WindowLoader.SHOW_AND_WAITING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@FXML
	public void handleOnClickAddingButton() {
		FileChooser fileChooser = new FileChooser();
		List<File> fileList = fileChooser.showOpenMultipleDialog(this.stage);
		List<Path> pathList = fileList.stream().map(files -> files.toPath())
				.collect(Collectors.toList());
		pathList.forEach(System.out::println);
		selectedPathList.addAll(pathList.stream()
				.map(path -> new SelectedPath(path))
				.collect(Collectors.toList()));
	}

	@FXML
	public void handleRenameButton() {
		this.selectedPathList
				.forEach(selectedPath -> {
					try {
						Files.move(selectedPath.getBeforeFullPath(),
								selectedPath.getAfterFullPath());
						this.outputTextArea.appendText(selectedPath.toString()
								+ "\n\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	@FXML
	public void handleClearButton() {
		this.selectedPathList.clear();
		this.outputTextArea.clear();
	}

	@FXML
	public void handleDeleteButton() {
		this.selectedPathList.remove(this.selectedPathTableView
				.getSelectionModel().getSelectedItem());
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
