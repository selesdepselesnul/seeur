package seeurrenamer.main;

import java.io.IOException;

import seeurrenamer.main.controller.SeeurRenamerMainWindowController;
import seeurrenamer.main.util.WindowLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			WindowLoader windowLoader = new WindowLoader(
					"seeurrenamer/main/view/SeeurRenamerMainWindow.fxml",
					"seeurrenamer/main/resources/style/seeur_renamer_main_window.css",
					"SEEUR ~BETA~",
					(fxmlLoader, stage) -> {
						SeeurRenamerMainWindowController seeurRenamerMainWindowController = (SeeurRenamerMainWindowController) fxmlLoader
								.getController();
						seeurRenamerMainWindowController.setStage(primaryStage);
					});
			windowLoader.setResizable(true);
			windowLoader.show(WindowLoader.SHOW_ONLY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
