package wip;

import java.util.Optional;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class DeleteGUI extends GUI {
	private Lock lock;

	private Label lblSerial = new MyLabel("Serial: ");
	private TextField txtSerial = new TextField("");
	private Button btnCheckForExisting = new MyButton("Check for existing lock");
	private Label lblBarcode = new MyLabel("Barcode: ");
	private Label lblBarcodeT = new MyLabel("-");
	private Label lblCombo = new MyLabel("Combo: ");
	private Label lblComboT = new MyLabel("-");
	private Label lblYearAdded = new MyLabel("Year Added: ");
	private Label lblYearAddedT = new MyLabel("-");
	private Label lblYearUsed = new MyLabel("Year Last Used: ");
	private Label lblYearUsedT = new MyLabel("-");
	private Label lblTotalUses = new MyLabel("Total Uses: ");
	private Label lblTotalUsesT = new MyLabel("-");
	private Label lblAssignedLocker = new MyLabel("Assigned Locker: ");
	private Label lblAssignedLockerT = new MyLabel("-");

	private Button btnDelete = new MyButton("Delete");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public DeleteGUI() {
		super(500, 450, "Delete Lock");

		// Label and text field for serial
		gpMain.add(lblSerial, 0, 0);
		gpMain.add(txtSerial, 1, 0);
		// Button to check if lock exist before continuing with form
		gpMain.add(btnCheckForExisting, 0, 1, 2, 1);
		GridPane.setHalignment(btnCheckForExisting, HPos.CENTER);
		btnCheckForExisting.setOnAction(e -> lookUp());
		// Label and text field for serial
		gpMain.add(lblBarcode, 0, 2);
		gpMain.add(lblBarcodeT, 1, 2);
		// Label and text field for combo
		gpMain.add(lblCombo, 0, 3);
		gpMain.add(lblComboT, 1, 3);
		// Label and text field for year added
		gpMain.add(lblYearAdded, 0, 4);
		gpMain.add(lblYearAddedT, 1, 4);
		// Label and text field for year used
		gpMain.add(lblYearUsed, 0, 5);
		gpMain.add(lblYearUsedT, 1, 5);
		// Label and text field for total uses
		gpMain.add(lblTotalUses, 0, 6);
		gpMain.add(lblTotalUsesT, 1, 6);
		// Label and text field for assigned locker
		gpMain.add(lblAssignedLocker, 0, 7);
		gpMain.add(lblAssignedLockerT, 1, 7);

		gpButtons.add(btnDelete, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnDelete.setDisable(true);

		btnDelete.setOnAction(e -> delete());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
		
		enterBtn = btnDelete;
	}

	// Looks up lock
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
			lblBarcodeT.setText("" + lock.getBarcode());
			lblComboT.setText(lock.getCombo());
			lblYearAddedT.setText("" + lock.getYearAdded());
			lblYearUsedT.setText("" + (lock.getYearLastUsed() == 3000 ? "-" : lock.getYearLastUsed()));
			lblTotalUsesT.setText("" + lock.getTotalUses());
			lblAssignedLockerT.setText(lock.getAssignedLocker());

			btnCheckForExisting.setDisable(true);
			txtSerial.setDisable(true);

			btnDelete.setDisable(false);
		}
	}

	// Clears the form
	private void clear() {
		lblBarcodeT.setText("-");
		lblBarcodeT.setText("-");
		lblComboT.setText("-");
		lblYearAddedT.setText("-");
		lblYearUsedT.setText("-");
		lblTotalUsesT.setText("-");
		lblAssignedLockerT.setText("-");

		btnCheckForExisting.setDisable(false);
		txtSerial.setDisable(false);
		btnDelete.setDisable(true);
		
		txtSerial.requestFocus();
	}

	// Deletes lock from DB
	private void delete() {
		boolean delete = true;
		boolean locker = false;
		boolean report = false;

		if(!lock.getAssignedLocker().equals("-")) {
			// If the lock is currently assigned to locker
			locker = true;

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Lock Assigned to Locker");
			alert.setHeaderText("WARNING!");
			alert.setContentText("This lock is currently assigned to a locker.\nCurrent locker: " + lock.getAssignedLocker() +"\nDeleting it will remove this assignment.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK){
				delete = false;
			}
		}
		
		int numOfReports = Main.getReportsForLock(lock.getSerial()).size();
		if(numOfReports > 0) {
			// If lock has reports on it
			report = true;

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Lock Assigned to Report");
			alert.setHeaderText("WARNING!");
			alert.setContentText("This lock is currently assigned to " + numOfReports + " " + (numOfReports == 1 ? "report." : "reports.") + "\nDeleting it will remove these reports.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK){
				delete = false;
			}
		}

		if(delete) {
			// Deletes the lock
			if(locker) {
				Main.clearAssignment(lock.getSerial());
			}
			
			if(report) {
				Main.deleteReportsByLock(lock.getSerial());
			}

			Main.log("Lock serial " + lock.getSerial() + " deleted.");
			Main.deleteLock(lock.getSerial());

			lblSuccess.setText("Success! Lock deleted.");
		}
	}
}
