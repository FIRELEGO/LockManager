package wip;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddLockGUI extends GUI {
	private Label lblSerial = new Label("Serial: ");
	private TextField txtSerial = new TextField("");
	private Label lblCombo = new Label("Combo(xx-xx-xx): ");
	private TextField txtCombo = new TextField("");
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
	private Label lblYearAdded = new Label("Year Added: ");
	private Label lblYearAddedT = new Label("-");

	private Button btnCheckForExisting = new MyButton("Check for Existing");
	private Button btnAdd = new MyButton("Add");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public AddLockGUI() {
		super(500, 450, "Add Lock");

		// Label and text field for serial
		gpMain.add(lblSerial, 0, 0);
		gpMain.add(txtSerial, 1, 0);
		// Button to check if lock exist before continuing with form
		gpMain.add(btnCheckForExisting, 0, 1, 2, 1);
		GridPane.setHalignment(btnCheckForExisting, HPos.CENTER);
		btnCheckForExisting.setOnAction(e -> checkButton());
		// Label and text field for combo
		gpMain.add(lblCombo, 0, 2);
		gpMain.add(txtCombo, 1, 2);
		txtCombo.setDisable(true);
		// Label and text field for barcode
		gpMain.add(lblBarcode, 0, 3);
		gpMain.add(txtBarcode, 1, 3);
		txtBarcode.setDisable(true);
		// Label and text field for year added
		gpMain.add(lblYearAdded, 0, 4);
		gpMain.add(lblYearAddedT, 1, 4);

		gpButtons.add(btnAdd, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnAdd.setDisable(true);

		btnAdd.setOnAction(e -> add());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
		
		enterBtn = btnAdd;
	}

	// Determines purpose of button and handles action
	private void checkButton() {
		if (btnCheckForExisting.getText().equals("Check for Existing")) {
			lookUp();
		} else {
			newSerial();
		}
	}

	// Adds the lock to the DB
	private void add() {
		lblError.setText("");

		try {
			int serial = Integer.parseInt(txtSerial.getText());
			String combo = txtCombo.getText();
			int barcode = Integer.parseInt(txtBarcode.getText());
			int yearAdded = Integer.parseInt(lblYearAddedT.getText());


			// Checks that numbers in xx-xx-xx combo are ints.
			if(combo.length() != 8) {
				lblError.setText("Make sure combo is in xx-xx-xx form.");
			} else if (Main.searchBarcode(barcode) != null) {
				lblError.setText("Barcode already in use.");
			} else {
				// Splits combo from xx-xx-xx to array then makes sure they're all numbers
				String[] comboNums = combo.split("-");
				for(String temp : comboNums) {
					// TODO check all nums are < 40
					Integer.parseInt(temp);
				}

				Main.log("Lock with serial " + serial + " added.");
				Main.addLock(serial, combo, barcode, yearAdded);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Add Lock Success");
				alert.setHeaderText("Success!");
				alert.setContentText("Lock was succesfully added.\nSerial: " + serial + "\tBarcode: " + barcode);

				alert.showAndWait();

				Main.setStage("Home");
			}
		} catch (NumberFormatException e) {
			lblError.setText("Check that the serial and barcode are integers.");
		}
	}

	// Locks up lock in old and new DB
	private void lookUp() {
		String sSerial = txtSerial.getText();
		clear();
		txtSerial.setText(sSerial);
		lblSuccess.setText("");
		lblError.setText("");

		try {
			Lock lock = Main.searchSerial(Integer.parseInt(txtSerial.getText()), true);

			if(lock != null) {
				txtCombo.setText(lock.getCombo());

				if(lock.getBarcode() == -1) {
					// If the lock does not have a barcode then it is from the old DB and needs to be imported
					txtBarcode.setDisable(false);
					lock.setYearAdded(Main.getCurYear());
					lblYearAddedT.setText("" + lock.getYearAdded());
					btnAdd.setDisable(false);
					lblSuccess.setText("Lock found in old DB. Enter remaining info.");
				} else {
					// Lock is already in DB
					txtBarcode.setText("" + lock.getBarcode());
					lblYearAddedT.setText("" + lock.getYearAdded());
					lblError.setText("Lock already in DB.");
				}
			} else {
				// Lock was not found in any DB
				txtBarcode.setDisable(false);
				txtCombo.setDisable(false);
				lblYearAddedT.setText("" + Main.getCurYear());
				btnAdd.setDisable(false);
				lblSuccess.setText("Lock not found. Enter info.");
			}

			txtSerial.setDisable(true);
			btnCheckForExisting.setText("Enter different serial");
		} catch (NumberFormatException e) {
			lblError.setText("Check that serial is an integer.");
		}
	}

	// Clears form
	private void clear() {
		txtSerial.setText("");
		txtSerial.setDisable(false);
		txtCombo.setText("");
		txtCombo.setDisable(true);
		txtBarcode.setText("");
		txtBarcode.setDisable(true);
		lblYearAddedT.setText("-");
		btnAdd.setDisable(true);
		btnCheckForExisting.setText("Check for Existing");
		
		txtSerial.requestFocus();
	}

	// Allows user to check a new serial
	private void newSerial() {
		txtSerial.setText("");
		txtSerial.setDisable(false);
		txtCombo.setText("");
		txtCombo.setDisable(true);
		txtBarcode.setText("");
		txtBarcode.setDisable(true);
		lblYearAddedT.setText("-");
		btnCheckForExisting.setText("Check for Existing");
	}
}
