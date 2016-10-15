package wip;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class AssignLockerGUI extends GUI {
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
	private Label lblLockerNum = new Label("Locker Number: ");
	private TextField txtLockerNum = new TextField("");
	private Label lblTotalUses = new Label("Total Uses: ");
	private Label lblTotalUsesT = new Label("-");
	private Label lblLastUsed = new Label("Last Used: ");
	private Label lblLastUsedT = new Label("-");
	private Label lblYearAdded = new Label("Year Added: ");
	private Label lblYearAddedT = new Label("-");
	private Button btnIncrement = new Button("+");
	
	private Button btnAssign = new MyButton("Assign");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public AssignLockerGUI() {
		super(500, 450, "Assign Locker");

		gpMain.add(lblBarcode, 0, 0);
		gpMain.add(txtBarcode, 1, 0);
		gpMain.add(lblLockerNum, 0, 1);
		gpMain.add(txtLockerNum, 1, 1);
		gpMain.add(btnIncrement, 2, 1);
		gpMain.add(lblTotalUses, 0, 2);
		gpMain.add(lblTotalUsesT, 1, 2);
		gpMain.add(lblLastUsed, 0, 3);
		gpMain.add(lblLastUsedT, 1, 3);
		gpMain.add(lblYearAdded, 0, 4);
		gpMain.add(lblYearAddedT, 1, 4);

		gpButtons.add(btnAssign, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnIncrement.setOnAction(e -> increment());
		btnAssign.setOnAction(e -> assign());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
	}
	
	private void increment() {
		lblError.setText("");
		try {
			int lockerNum = Integer.parseInt(txtLockerNum.getText());
			
			txtLockerNum.setText("" + ++lockerNum);
		} catch (NumberFormatException e) {
			lblError.setText("Check that current locker number is an integer.");
		}
	}

	private void assign() {
		lblError.setText("");

		try {
			int barcode = Integer.parseInt(txtBarcode.getText());
			int lockerNum = Integer.parseInt(txtLockerNum.getText());
			Lock lock = Main.searchBarcode(barcode);
			
			if (lock != null) {
				Main.assignLocker(lock.getSerial(), "" + lockerNum, Integer.parseInt(Main.getCurYear()), lock.getTotalUses());
				
				lblError.setText("Success! Barcode: " + lock.getBarcode() + " Locker: " + lockerNum);
				
				Lock newLock = Main.searchSerial(lock.getSerial(), false);
				lblTotalUsesT.setText("" + newLock.getTotalUses());
				lblLastUsedT.setText("" + newLock.getYearLastUsed());
				lblYearAddedT.setText("" + newLock.getYearAdded());

				txtBarcode.setText("");
			} else {
				lblError.setText("Barcode not found.");
			}
		} catch (NumberFormatException e) {
			lblError.setText("Check that the barcode and locker number are integers.");
		}
	}

	private void clear() {
		txtBarcode.setText("");
	}
}
