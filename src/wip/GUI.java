package wip;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public abstract class GUI {
	// Main grid pane that everything is part of
	private GridPane gpRoot;
	// Top grid pane that has the program title and version change
	private GridPane gpInfo;
	// Middle grid pane that has all of the components for the specific GUI
	protected GridPane gpMain;
	// Bottom grid pane that has all of the navigation buttons
	protected GridPane gpButtons;
	// program title and version change
	private Label lblHeader = new Label("\nLock Manager\t\tV" + Main.VERSION);
	// Green label used to notify the user of a success without requiring acknowledgment  
	protected Label lblSuccess = new Label("");
	// Red label used to notify the user of a failure without requiring acknowledgment  
	protected Label lblError = new Label("");
	// Height of the GUI
	private int height;
	// Width of the GUI
	private int width;
	// Title of the GUI
	private String name;
	// Node that is focused on load
	protected Node focus;
	// Button tied to enter key
	protected Button enterBtn;

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

		lblSuccess.setTextFill(Color.GREEN);
		lblError.setTextFill(Color.RED);

		gpRoot.add(gpInfo, 0, 0);
		gpRoot.add(lblSuccess, 0, 1);
		gpRoot.add(gpMain, 0, 2);
		gpRoot.add(gpButtons, 0, 3);
		gpRoot.add(lblError, 0, 4);
	}

	// Builds and returns scene
	public Scene getScene() {
		Scene scene = new Scene(gpRoot, width, height);
		if(enterBtn != null) {
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

	            @Override
	            public void handle(KeyEvent event) {
	                if (event.getCode() == KeyCode.ENTER) {
	                    enterBtn.fire();
	                }
	            }
	        });
		}
		return scene;
	}

	public String getName() {
		return name;
	}

	// Gets the dimension of the screen
	public int[] getDim() {
		return new int[] { width, height };
	}
	
	public Node getFocusElement() {
		return focus;
	}
}
