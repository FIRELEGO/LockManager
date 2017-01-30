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

	private Label lblSerial = new Label("Serial: ");
	private TextField txtSerial = new TextField("");
	private Button btnCheckForExisting = new MyButton("Check for existing lock");
	private Label lblBarcode = new Label("Barcode: ");
	private Label lblBarcodeT = new Label("-");
	private Label lblCombo = new Label("Combo: ");
	private Label lblComboT = new Label("-");
	private Label lblYearAdded = new Label("Year Added: ");
	private Label lblYearAddedT = new Label("-");
	private Label lblYearUsed = new Label("Year Last Used: ");
	private Label lblYearUsedT = new Label("-");
	private Label lblTotalUses = new Label("Total Uses: ");
	private Label lblTotalUsesT = new Label("-");
	private Label lblAssignedLocker = new Label("Assigned Locker: ");
	private Label lblAssignedLockerT = new Label("-");

	private Button btnDelete = new MyButton("Delete");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public DeleteGUI() {
		super(500, 450, "Look Up Lock");

		gpMain.add(lblSerial, 0, 0);
		gpMain.add(txtSerial, 1, 0);
		gpMain.add(btnCheckForExisting, 0, 1, 2, 1);
		GridPane.setHalignment(btnCheckForExisting, HPos.CENTER);
		btnCheckForExisting.setOnAction(e -> lookUp());
		gpMain.add(lblBarcode, 0, 2);
		gpMain.add(lblBarcodeT, 1, 2);
		gpMain.add(lblCombo, 0, 3);
		gpMain.add(lblComboT, 1, 3);
		gpMain.add(lblYearAdded, 0, 4);
		gpMain.add(lblYearAddedT, 1, 4);
		gpMain.add(lblYearUsed, 0, 5);
		gpMain.add(lblYearUsedT, 1, 5);
		gpMain.add(lblTotalUses, 0, 6);
		gpMain.add(lblTotalUsesT, 1, 6);
		gpMain.add(lblAssignedLocker, 0, 7);
		gpMain.add(lblAssignedLockerT, 1, 7);

		gpButtons.add(btnDelete, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnDelete.setDisable(true);

		btnDelete.setOnAction(e -> delete());
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
	}

	private void delete() {
		boolean delete = true;
		boolean locker = false;
		
		if(!lock.getAssignedLocker().equals("-")) {
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

		if(delete) {
			if(locker) {
				Main.clearAssignment(lock.getSerial());
			}
			
			Main.deleteLock(lock.getSerial());

			lblSuccess.setText("Success! Lock deleted.");
		}
	}
}