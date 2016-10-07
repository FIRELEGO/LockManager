/* Nicholas Signori
 * Bank
 */
package wip;

import javafx.application.Application;
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
}
