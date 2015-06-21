package seeurrenamer.main.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * @author moch deden
 *
 */
public class RegexSummaryWindowController implements Initializable {

	@FXML
	private TextArea regexSummaryTextArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							ClassLoader
									.getSystemResourceAsStream("seeurrenamer/main/resources/text/regex_summary.txt")));
			this.regexSummaryTextArea.setText(bufferedReader.lines().collect(
					Collectors.joining("\n")));
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
