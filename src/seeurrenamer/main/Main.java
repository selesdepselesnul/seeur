package seeurrenamer.main;

import java.io.IOException;

import seeurrenamer.main.controller.SeeurMainWindowController;
import seeurrenamer.main.util.WindowLoader;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author moch deden 
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			WindowLoader windowLoader = new WindowLoader(
					"seeurrenamer/main/view/SeeurMainWindow.fxml",
					"seeurrenamer/main/resources/style/seeur_renamer_main_window.css",
					"SEEUR ~1.0.0~",
					(fxmlLoader, stage) -> {
						SeeurMainWindowController seeurMainWindowController = (SeeurMainWindowController) fxmlLoader
								.getController();
						seeurMainWindowController.setStage(primaryStage);
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
