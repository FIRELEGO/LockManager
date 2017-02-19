package wip;

import java.util.Optional;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AssignBarcodeGUI extends GUI {
	private Label lblSerial = new Label("Serial: ");
	private TextField txtSerial = new TextField("");
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
	private Label lblCombo = new Label("Combo: ");
	private Label lblComboT = new Label("-");
	private Label lblYearAdded = new Label("Year Added: ");
	private Label lblYearAddedT = new Label("-");
	private Label lblYearLastUsed = new Label("Year Last Used: ");
	private Label lblYearLastUsedT = new Label("-");
	private Label lblTotalUses = new Label("Total Uses: ");
	private Label lblTotalUsesT = new Label("-");

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
		gpMain.add(lblYearAdded, 0, 4);
		gpMain.add(lblYearAddedT, 1, 4);
		gpMain.add(lblYearLastUsed, 0, 5);
		gpMain.add(lblYearLastUsedT, 1, 5);
		gpMain.add(lblTotalUses, 0, 6);
		gpMain.add(lblTotalUsesT, 1, 6);

		gpButtons.add(btnAssign, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnAssign.setDisable(true);

		btnAssign.setOnAction(e -> assign());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
		
		enterBtn = btnAssign;
	}

	private void assign() {
		lblError.setText("");

		try {
			int serial = Integer.parseInt(txtSerial.getText());
			int barcode = Integer.parseInt(txtBarcode.getText());

			if (Main.searchBarcode(barcode) == null) {
				boolean assign = true;
				if(Main.searchSerial(serial, false).getBarcode() != -1 && Main.searchSerial(serial, false).getBarcode() != barcode) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Lock already assigned to barcode");
					alert.setHeaderText("WARNING!");
					alert.setContentText("This lock is currently assigned to barcode " + Main.searchSerial(serial, false).getBarcode() + " it will be assigned to barcode " + barcode + "\nClicking \"Ok\" will reassign the lock.");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() != ButtonType.OK){
						assign = false;
					}
				}

				if(assign) {
					Main.log("Barcode " + barcode + " assiged to lock serial " + serial);
					Main.editBarcode(serial, barcode);

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Barcode Change Success");
					alert.setHeaderText("Success!");
					alert.setContentText("Barcode was succesfully changed.\nSerial: " + serial + "\tBarcode: " + barcode);

					alert.showAndWait();

					Main.setStage("Home");
				}
			} else {
				lblError.setText("Barcode already in use.");
			}
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
				lblYearAddedT.setText("" + lock.getYearAdded());
				lblYearLastUsedT.setText("" + (lock.getYearLastUsed() == 3000 ? "-" : lock.getYearLastUsed()));
				lblTotalUsesT.setText("" + lock.getTotalUses());
				btnAssign.setDisable(false);
				
				txtBarcode.requestFocus();
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
		lblYearAddedT.setText("-");
		lblYearLastUsedT.setText("-");
		lblTotalUsesT.setText("-");
		btnAssign.setDisable(true);
		
		txtSerial.requestFocus();
	}
}
