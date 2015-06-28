package seeurrenamer.main.util.gui;

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

/**
 * javafx dialog class that can be used for find all paths in any choosen
 * directory, it can traverse recursivelly inside choosen directory
 * 
 * @author moch deden
 *
 */
public class PathsFinder {

	private File choosenDir;

	/**
	 * @param stage
	 *            owner window of the displayed dialog
	 * 
	 */
	public PathsFinder(Stage stage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		choosenDir = directoryChooser.showDialog(stage);
	}

	/**
	 * return {@link List} of {@link PairPath} that match particular regex
	 * pattern
	 * 
	 * @param regex
	 *            pattern for searched paths
	 * @return {@link List} of {@link PairPath}
	 * @throws IOException
	 */
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
