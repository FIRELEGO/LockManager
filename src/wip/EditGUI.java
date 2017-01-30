package wip;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EditGUI extends GUI {
	private Lock lock;

	private Label lblSerial = new Label("Serial: ");
	private TextField txtSerial = new TextField("");
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
	private Button btnCheckForExisting = new MyButton("Check for Existing");
	private Label lblCombo = new Label("Combo: ");
	private TextField txtCombo = new TextField("");
	private Label lblYearAdded = new Label("Year Added: ");
	private TextField txtYearAdded = new TextField("");
	private Label lblYearUsed = new Label("Year Last Used: ");
	private TextField txtYearUsed = new TextField("");
	private Label lblTotalUses = new Label("Total Uses: ");
	private TextField txtTotalUses = new TextField("");
	private Label lblAssignedLocker = new Label("Assigned Locker: ");
	private Label lblAssignedLockerT = new Label("-");

	private Button btnChangeInfo = new MyButton("Change Info");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public EditGUI() {
		super(500, 450, "Look Up Lock");

		gpMain.add(lblSerial, 0, 0);
		gpMain.add(txtSerial, 1, 0);
		gpMain.add(btnCheckForExisting, 0, 1, 2, 1);
		GridPane.setHalignment(btnCheckForExisting, HPos.CENTER);
		btnCheckForExisting.setOnAction(e -> lookUp());
		gpMain.add(lblBarcode, 0, 2);
		gpMain.add(txtBarcode, 1, 2);
		gpMain.add(lblCombo, 0, 3);
		gpMain.add(txtCombo, 1, 3);
		gpMain.add(lblYearAdded, 0, 4);
		gpMain.add(txtYearAdded, 1, 4);
		gpMain.add(lblYearUsed, 0, 5);
		gpMain.add(txtYearUsed, 1, 5);
		gpMain.add(lblTotalUses, 0, 6);
		gpMain.add(txtTotalUses, 1, 6);
		gpMain.add(lblAssignedLocker, 0, 7);
		gpMain.add(lblAssignedLockerT, 1, 7);

		txtBarcode.setDisable(true);
		txtCombo.setDisable(true);
		txtYearAdded.setDisable(true);
		txtYearUsed.setDisable(true);
		txtTotalUses.setDisable(true);

		gpButtons.add(btnChangeInfo, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnChangeInfo.setDisable(true);

		btnChangeInfo.setOnAction(e -> change());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
	}

	private void lookUp() {
		lblError.setText("");

		lock = null;
		try {
			lock = Main.searchSerial(Integer.parseInt(txtSerial.getText()), false);
		} catch (NumberFormatException e) {
			lblError.setText("Check that serial is an integer.");
		}

		if (lock == null) {
			lblError.setText("Lock not found in database.");
		} else {
			txtSerial.setText("" + lock.getSerial());
			txtBarcode.setText("" + lock.getBarcode());
			txtCombo.setText(lock.getCombo());
			txtYearAdded.setText("" + lock.getYearAdded());
			txtYearUsed.setText("" + (lock.getYearLastUsed() == 3000 ? "" : lock.getYearLastUsed()));
			txtTotalUses.setText("" + lock.getTotalUses());
			lblAssignedLockerT.setText(lock.getAssignedLocker());

			txtSerial.setDisable(true);
			txtBarcode.setDisable(false);
			txtCombo.setDisable(false);
			txtYearAdded.setDisable(false);
			txtYearUsed.setDisable(false);
			txtTotalUses.setDisable(false);

			btnCheckForExisting.setDisable(true);

			btnChangeInfo.setDisable(false);
		}
	}

	private void change() {
		lblError.setText("");
		lblSuccess.setText("");
		try {
			if(Main.searchBarcode(Integer.parseInt(txtBarcode.getText())) == null) {
			Main.changeLock(lock, new Lock(Integer.parseInt(txtSerial.getText()), txtCombo.getText(), Integer.parseInt(txtBarcode.getText()), Integer.parseInt(txtYearAdded.getText()), Integer.parseInt(txtYearUsed.getText()), Integer.parseInt(txtTotalUses.getText()), lblAssignedLockerT.getText()));
			lblSuccess.setText("Lock info chagned.");
			clear();
			} else {
				lblError.setText("Barcode already in use.");
			}
		} catch(NumberFormatException e) {
			lblError.setText("Check that all info is of the correct format.");
		}
	}

	private void clear() {
		txtSerial.setText("");
		txtBarcode.setText("");
		txtBarcode.setText("");
		txtCombo.setText("");
		txtYearAdded.setText("");
		txtYearUsed.setText("");
		txtTotalUses.setText("");
		lblAssignedLockerT.setText("-");

		txtSerial.setDisable(false);
		txtBarcode.setDisable(true);
		txtCombo.setDisable(true);
		txtYearAdded.setDisable(true);
		txtYearUsed.setDisable(true);
		txtTotalUses.setDisable(true);

		btnCheckForExisting.setDisable(false);
		btnChangeInfo.setDisable(true);
	}

}
