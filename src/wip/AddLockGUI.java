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
	private Button btnLookUp = new MyButton("Look Up");
	private Label lblCombo = new Label("Combo(xx-xx-xx): ");
	private TextField txtCombo = new TextField("");
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
	private Label lblDateAdded = new Label("Date Added: ");
	private Label lblDateAddedT = new Label("-");

	private Button btnCheckForExisting = new MyButton("Check for Existing");
	private Button btnAdd = new MyButton("Add");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public AddLockGUI() {
		super(500, 450, "Add Lock");

		gpMain.add(lblSerial, 0, 0);
		gpMain.add(txtSerial, 1, 0);
		gpMain.add(btnCheckForExisting, 0, 1, 2, 1);
		GridPane.setHalignment(btnCheckForExisting, HPos.CENTER);
		btnCheckForExisting.setOnAction(e -> checkButton());
		gpMain.add(lblCombo, 0, 2);
		gpMain.add(txtCombo, 1, 2);
		txtCombo.setDisable(true);
		gpMain.add(lblBarcode, 0, 3);
		gpMain.add(txtBarcode, 1, 3);
		txtBarcode.setDisable(true);
		gpMain.add(lblDateAdded, 0, 4);
		gpMain.add(lblDateAddedT, 1, 4);

		gpButtons.add(btnAdd, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnAdd.setDisable(true);

		btnAdd.setOnAction(e -> add());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
	}

	private void checkButton() {
		if (btnCheckForExisting.getText().equals("Check for Existing")) {
			lookUp();
		} else {
			newSerial();
		}
	}

	private void add() {
		lblError.setText("");

		try {
			int serial = Integer.parseInt(txtSerial.getText());
			String combo = txtCombo.getText();
			int barcode = Integer.parseInt(txtBarcode.getText());
			String dateAdded = lblDateAddedT.getText();


			// Checks that numbers in xx-xx-xx combo are ints.
			if(combo.length() != 8) {
				lblError.setText("Make sure combo is in xx-xx-xx form.");
			} else {
				String[] comboNums = combo.split("-");
				for(String temp : comboNums) {
					Integer.parseInt(temp);
				}

				Main.addLock(serial, combo, barcode, dateAdded);

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

	private void newSerial() {
		txtSerial.setText("");
		txtSerial.setDisable(false);
		txtCombo.setText("");
		txtCombo.setDisable(true);
		txtBarcode.setText("");
		txtBarcode.setDisable(true);
		lblDateAddedT.setText("-");
		btnCheckForExisting.setText("Check for Existing");
	}

	private void lookUp() {
		String sSerial = txtSerial.getText();
		clear();
		txtSerial.setText(sSerial);
		lblError.setText("");

		try {
			Lock lock = Main.searchSerial(Integer.parseInt(txtSerial.getText()), true);

			if(lock != null) {
				txtCombo.setText(lock.getCombo());

				if(lock.getBarcode() == -1) {
					txtBarcode.setDisable(false);
					lock.setDateAdded(Main.getCurDate());
					lblDateAddedT.setText(lock.getDateAdded());
					btnAdd.setDisable(false);
					lblError.setText("Lock found in old DB. Enter remaining info.");
				} else {
					txtBarcode.setText("" + lock.getBarcode());
					lblDateAddedT.setText(lock.getDateAdded());
					lblError.setText("Lock already in DB.");
				}
			} else {
				txtBarcode.setDisable(false);
				txtCombo.setDisable(false);
				lblDateAddedT.setText(Main.getCurDate());
				btnAdd.setDisable(false);
				lblError.setText("Lock not found. Enter info.");
			}

			txtSerial.setDisable(true);
			btnCheckForExisting.setText("Enter different serial");
		} catch (NumberFormatException e) {
			lblError.setText("Check that serial is an integer.");
		}
	}

	private void clear() {
		txtSerial.setText("");
		txtCombo.setText("");
		txtCombo.setDisable(true);
		txtBarcode.setText("");
		txtBarcode.setDisable(true);
		lblDateAddedT.setText("-");
		btnAdd.setDisable(true);
	}
}
