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

		// Label and text field for barcode
		gpMain.add(lblBarcode, 0, 0);
		gpMain.add(txtBarcode, 1, 0);
		// Label and text field for locker number
		gpMain.add(lblLockerNum, 0, 1);
		gpMain.add(txtLockerNum, 1, 1);

		// Button to increment locker number
		gpMain.add(btnIncrement, 2, 1);
		// Label and text field for total uses
		gpMain.add(lblTotalUses, 0, 2);
		gpMain.add(lblTotalUsesT, 1, 2);
		// Label and text field for year lock was last used
		gpMain.add(lblLastUsed, 0, 3);
		gpMain.add(lblLastUsedT, 1, 3);
		// Label and text field for year added
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

	// Increments locker number
	private void increment() {
		lblError.setText("");
		try {
			int lockerNum = Integer.parseInt(txtLockerNum.getText().substring(txtLockerNum.getText().indexOf("-") + 1));
			int floor = Integer.parseInt(txtLockerNum.getText().substring(0, txtLockerNum.getText().indexOf("-")));
			
			++lockerNum;
			txtLockerNum.setText(floor + "-" + (("" + lockerNum).length() >= 3 ? "" : (("" + lockerNum).length() == 2) ? "0" : "00") + lockerNum);
		} catch (NumberFormatException e) {
			lblError.setText("Make sure locker number is in F-XXX form where F is the floor and XXX is the locker number.");
		}
	}

	// Assigns lock to locker
	private void assign() {
		lblError.setText("");

		try {
			int barcode = Integer.parseInt(txtBarcode.getText());
			String lockerNum = txtLockerNum.getText();
			Lock lock = Main.searchBarcode(barcode);

			if(lockerNum.length() != 5) {
				lblError.setText("Make sure locker number is in F-XXX form where F is the floor and XXX is the locker number.");
			} else if (lock != null) {
				// Checks if lock is assigned to a locker already of if locker already has a lock
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

					// Allows user to continue with assignment and break any other assignments
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Lock Already Assigned");
					alert.setHeaderText("WARNING!");
					alert.setContentText(message + "\nWould you like to remove these assignments and continue with the assignment?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() != ButtonType.OK){
						// Cancels lock assignment
						assign = false;
					}
				}

				if(assign) {
					// Assigns the lock to locker
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
				// Lock not found in DB
				lblError.setText("Barcode not found.");
			}
		} catch (NumberFormatException e) {
			lblError.setText("Check that the barcode and locker number are integers.");
		}
	}

	// Clears the form
	private void clear() {
		txtBarcode.setText("");

		txtBarcode.requestFocus();
	}
}
