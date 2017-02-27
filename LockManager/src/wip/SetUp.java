package wip;

import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SetUp extends Application {

	public static Stage stage = null;
	private static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
	private static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();

	@Override
	public void start(Stage stage) throws Exception {
		SetUp.stage = stage;
		// Sets icon
		stage.getIcons().add(new Image("file:res/lock.png"));
		
		// Sets close action
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override public void handle(WindowEvent t) {
		        Main.exit();
		    }
		});
		
		setScene(new LoginGUI());
	}

	// Changes stage to new scene
	public static void setScene(GUI scene) {
		stage.setScene(scene.getScene());
		stage.setTitle(scene.getName());
		int[] dim = scene.getDim(); // 0 = width, 1 = height
		stage.setX((screenWidth / 2) - (dim[0] / 2));
		stage.setY((screenHeight / 2) - (dim[1] / 2));
		stage.show();
		if(scene.getFocusElement() != null) {
			scene.getFocusElement().requestFocus();
		}
	}

	// File chooser for reports
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
