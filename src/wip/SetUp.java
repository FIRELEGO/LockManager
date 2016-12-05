/* Nicholas Signori
 * Bank
 */
package wip;

import java.io.File;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SetUp extends Application {

	public static Stage stage = new Stage();
	private static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
	private static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();

	@Override
	public void start(Stage stage) throws Exception {
		setScene(new HomeGUI());
	}

	public static void setScene(GUI scene) {
		stage.setScene(scene.getScene());
		stage.setTitle(scene.getName());
		int[] dim = scene.getDim(); // 0 = width, 1 = height
		stage.setX((screenWidth / 2) - (dim[0] / 2));
		stage.setY((screenHeight / 2) - (dim[1] / 2));
		stage.show();
	}

	public static String openFC(File directory, String name) {
		String ret = "";

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file save location");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf"));
		fileChooser.setInitialDirectory(directory);
		fileChooser.setInitialFileName(name);
		File selectedFile = fileChooser.showSaveDialog(stage);
		if (selectedFile != null) {
			ret = selectedFile.getAbsolutePath();
		}

		return ret;
	}
}
