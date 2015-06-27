package seeurrenamer.main.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import seeurrenamer.main.model.PairPath;

public class PathsFinder {

	private File choosenDir;

	public PathsFinder(Stage stage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		choosenDir = directoryChooser.showDialog(stage);
	}

	public List<PairPath> search(String regex) throws IOException {
		return search(path -> !Files.isDirectory(path)
				&& path.getFileName().toString().matches(regex));
	}

	public List<PairPath> search() throws IOException {
		return search(path -> !Files.isDirectory(path));
	}

	private List<PairPath> search(Predicate<? super Path> predicate)
			throws IOException {
		return Files.walk(this.choosenDir.toPath()).filter(predicate)
				.map(filteredPath -> new PairPath(filteredPath))
				.collect(Collectors.toList());
	}

}
