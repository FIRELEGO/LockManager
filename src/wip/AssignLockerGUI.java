package wip;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
		
		enterBtn = btnAssign;
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
			String lockerNum = txtLockerNum.getText();
			Lock lock = Main.searchBarcode(barcode);

			if (lock != null) {
			String[][] assignments = Main.checkAssignment(lockerNum, lock.getSerial());
				boolean assign = true;
				if(assignments[0][0] != null || assignments[1][0] != null) {
					String message = "";
					if(assignments[0][0] != null) {
						if(assignments[0][0].equals(lockerNum)) {
							message += "The locker already has lock serial: " + assignments[0][1];
						} else if(assignments[0][1].equals("" + lock.getSerial())) {
							message += "This lock is currently assigned to a locker number: " + assignments[0][0];
						}
					}
					if(assignments[1][0] != null) {
						if(assignments[1][0].equals(lockerNum)) {
							message += (message.isEmpty() ? "" : "\n") + "The locker already has lock serial: " + assignments[1][1];
						} else if(assignments[1][1].equals("" + lock.getSerial())) {
							message += (message.isEmpty() ? "" : "\n") + "This lock is currently assigned to a locker number: " + assignments[1][0];
						}
					}
					
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Lock Already Assigned");
					alert.setHeaderText("WARNING!");
					alert.setContentText(message + "\nWould you like to remove these assignments and continue with the assignment?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() != ButtonType.OK){
					    assign = false;
					}
				}

				if(assign) {
					Main.log("Lock serial " + lock.getSerial() + " assiged to locker number " + lockerNum);
					Main.assignLocker(lock.getSerial(), "" + lockerNum, Main.getCurYear(), lock.getTotalUses());

					lblSuccess.setText("Success! Barcode: " + lock.getBarcode() + " Locker: " + lockerNum);

					Lock newLock = Main.searchSerial(lock.getSerial(), false);
					lblTotalUsesT.setText("" + newLock.getTotalUses());
					lblLastUsedT.setText("" + newLock.getYearLastUsed());
					lblYearAddedT.setText("" + newLock.getYearAdded());

					txtBarcode.setText("");
				}
			} else {
				lblError.setText("Barcode not found.");
			}
		} catch (NumberFormatException e) {
			lblError.setText("Check that the barcode and locker number are integers.");
		}
	}

	private void clear() {
		txtBarcode.setText("");
		
		txtBarcode.requestFocus();
	}
}
