package wip;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class AssignBarcodeGUI extends GUI {
	private int serial = -1;
	private Label lblSerial = new Label("Serial: ");
	private TextField txtSerial = new TextField("");
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
	private Label lblCombo = new Label("Combo: ");
	private Label lblComboT = new Label("-");
	private Label lblDateAdded = new Label("Date Added: ");
	private Label lblDateAddedT = new Label("-");
	private Label lblYear = new Label("Year Last Used: ");
	private Label lblYearT = new Label("-");

	private Button btnCheckForExisting = new MyButton("Check for Lock");
	private Button btnAssign = new MyButton("Assign");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public AssignBarcodeGUI() {
		super(500, 450, "Assign Barcode");

		gpMain.add(lblSerial, 0, 0);
		gpMain.add(txtSerial, 1, 0);
		gpMain.add(btnCheckForExisting, 0, 1, 2, 1);
		GridPane.setHalignment(btnCheckForExisting, HPos.CENTER);
		btnCheckForExisting.setOnAction(e -> lookUp());
		gpMain.add(lblBarcode, 0, 2);
		gpMain.add(txtBarcode, 1, 2);
		txtBarcode.setDisable(true);
		gpMain.add(lblCombo, 0, 3);
		gpMain.add(lblComboT, 1, 3);
		gpMain.add(lblDateAdded, 0, 4);
		gpMain.add(lblDateAddedT, 1, 4);
		gpMain.add(lblYear, 0, 5);
		gpMain.add(lblYearT, 1, 5);

		gpButtons.add(btnAssign, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnAssign.setDisable(true);

		btnAssign.setOnAction(e -> assign());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
	}

	private void assign() {
		lblError.setText("");
		
		try {
			int serial = Integer.parseInt(txtSerial.getText());
			int barcode = Integer.parseInt(txtBarcode.getText());

			Main.editBarcode(serial, barcode);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Barcode Change Success");
			alert.setHeaderText("Success!");
			alert.setContentText("Barcode was succesfully changed.\nSerial: " + serial + "\tBarcode: " + barcode);

			alert.showAndWait();
			
			Main.setStage("Home");
		} catch (NumberFormatException e) {
			lblError.setText("Check that the serial and barcode are integers.");
		}
	}

	private void lookUp() {
		lblError.setText("");
		try {
			Lock lock = Main.searchSerial(Integer.parseInt(txtSerial.getText()), false);

			if(lock != null) {
				txtBarcode.setText("" + lock.getBarcode());
				txtBarcode.setDisable(false);
				lblComboT.setText(lock.getCombo());
				lblDateAddedT.setText(lock.getDateAdded());
				lblYearT.setText("" + lock.getYearLastUsed());
				btnAssign.setDisable(false);
			} else {
				lblError.setText("Lock not found. Check serial and try again.");
			}
		} catch(NumberFormatException e) {
			lblError.setText("Enter an integer in serial.");
		}
	}

	private void clear() {
		txtSerial.setText("");
		txtBarcode.setText("");
		lblComboT.setText("-");
		lblDateAddedT.setText("-");
		lblYearT.setText("-");
		btnAssign.setDisable(true);
	}
}
