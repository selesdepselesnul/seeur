package seeurrenamer.main.util.gui;

import java.io.IOException;
import java.util.function.BiConsumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author moch deden
 *
 */
public class WindowLoader {
	public static final int SHOW_AND_WAITING = 0;
	public static final int SHOW_ONLY = 1;
	private Stage stage;
	private String stringRepresentation;

	public WindowLoader(String sourceResource, String windowTitle,
			BiConsumer<FXMLLoader, Stage> biConsumer) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(
				ClassLoader.getSystemResource(sourceResource));
		Parent root = fxmlLoader.load();
		this.stage = new Stage();
		Scene scene = new Scene(root);
		this.stage.setScene(scene);
		this.stage.setTitle(windowTitle);
		this.stage.setResizable(false);
		this.stage.initModality(Modality.APPLICATION_MODAL);
		if (biConsumer != null) {
			biConsumer.accept(fxmlLoader, this.stage);
		}
		this.stage
				.getIcons()
				.add(new Image(
						ClassLoader
								.getSystemResourceAsStream("seeurrenamer/main/resources/style/image/main_icon.png")));
	}

	public WindowLoader(String sourceResource, String styleResource,
			String windowTitle, BiConsumer<FXMLLoader, Stage> biConsumer)
			throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(
				ClassLoader.getSystemResource(sourceResource));
		Parent root = fxmlLoader.load();
		this.stage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(
				ClassLoader.getSystemResource(styleResource).toExternalForm());
		this.stage.setScene(scene);
		this.stage.setTitle(windowTitle);
		this.stage.setResizable(false);
		this.stage.initModality(Modality.APPLICATION_MODAL);
		if (biConsumer != null) {
			biConsumer.accept(fxmlLoader, this.stage);
		}

		this.stage
				.getIcons()
				.add(new Image(
						ClassLoader
								.getSystemResourceAsStream("seeurrenamer/main/resources/style/image/main_icon.png")));

	}

	public void setResizable(boolean isResizeable) {
		this.stage.setResizable(isResizeable);
	}

	public void show(int mode) {
		if (mode == SHOW_AND_WAITING) {
			stage.showAndWait();
		} else if (mode == SHOW_ONLY) {
			stage.show();
		} else {
			throw new IllegalArgumentException("Invalid mode");
		}
	}

	public void setIcon(String imagePathStr) {
		stage.getIcons().add(
				new Image(ClassLoader.getSystemResourceAsStream(imagePathStr)));

	}

	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	@Override
	public String toString() {
		return this.stringRepresentation;
	}

}