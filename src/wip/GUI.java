/* Nicholas Signori
 * Bank
 */
package wip;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public abstract class GUI {
	private GridPane gpRoot;
	private GridPane gpInfo;
	protected GridPane gpMain;
	protected GridPane gpButtons;
	private Label lblHeader = new Label("\nLock Manager\t\tV" + Main.VERSION);
	protected Label lblError = new Label("");
	private int height;
	private int width;
	private String name;
	
	public GUI(int width, int height, String name) {
		this.height = height;
		this.width = width;
		this.name = name;
		
		gpRoot = new GridPane();
		gpInfo = new GridPane();
		gpMain = new GridPane();
		gpButtons = new GridPane();

		gpRoot.setAlignment(Pos.TOP_CENTER);
		gpRoot.setHgap(8);
		gpRoot.setVgap(8);
		
		gpInfo.setAlignment(Pos.TOP_CENTER);
		gpInfo.setHgap(8);
		gpInfo.setVgap(8);
		gpInfo.add(lblHeader, 0, 0);
		GridPane.setHalignment(lblHeader, HPos.CENTER);
		
		gpMain.setAlignment(Pos.TOP_CENTER);
		gpMain.setHgap(8);
		gpMain.setVgap(8);
		
		gpButtons.setAlignment(Pos.TOP_CENTER);
		gpButtons.setHgap(8);
		gpButtons.setVgap(8);
		
		lblError.setTextFill(Color.RED);
		
		gpRoot.add(gpInfo, 0, 0);
		gpRoot.add(gpMain, 0, 1);
		gpRoot.add(new Label("\n"), 0, 2);
		gpRoot.add(gpButtons, 0, 3);
		gpRoot.add(lblError, 0, 4);
		
		gpRoot.setBackground(new Background(new BackgroundFill(new Color(1, 0, 0, 1), new CornerRadii(0), new Insets(0))));
	}
	
	public Scene getScene() {
		return new Scene(gpRoot, width, height);
	}
	
	public String getName() {
		return name;
	}
	
	public int[] getDim() {
		return new int[] {width, height};
	}
}
