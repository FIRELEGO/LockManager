package wip;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class UnlockGUI extends GUI {
	private Lock lock;
	private String combo;

	private ToggleGroup tgSelector = new ToggleGroup();
	private RadioButton rbSerial = new RadioButton("Serial");
	private RadioButton rbBarcode = new RadioButton("Barcode");
	private RadioButton rbCombonation = new RadioButton("Combonation");
	private Label lblSerial = new MyLabel("Serial: ");
	private TextField txtSerial = new TextField("");
	private Label lblSerialT = new MyLabel("-");
	private Label lblBarcode = new MyLabel("Barcode: ");
	private TextField txtBarcode = new TextField("");
	private Label lblBarcodeT = new MyLabel("-");
	private Label lblCombo = new MyLabel("Combo: ");
	private Label lblComboT = new MyLabel("-");
	private TextField txtCombo = new TextField();
	private Button btnCheckForExisting = new MyButton("Check for Existing");

	private Button btnUnlock = new MyButton("Unlock");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public UnlockGUI() {
		super(500, 450, "Unlock lock");

		rbSerial.setToggleGroup(tgSelector);
		rbBarcode.setToggleGroup(tgSelector);
		rbCombonation.setToggleGroup(tgSelector);
		rbSerial.setOnAction(e -> setSerialMode());
		rbBarcode.setOnAction(e -> setBarcodeMode());
		rbCombonation.setOnAction(e -> setComboMode());

		gpMain.add(rbSerial, 0, 1);
		rbSerial.setSelected(true);
		gpMain.add(rbBarcode, 1, 1);
		gpMain.add(rbCombonation, 2, 1);
		// Label and text field for serial
		gpMain.add(lblSerial, 0, 2, 2, 1);
		gpMain.add(txtSerial, 1, 2, 2, 1);
		gpMain.add(lblSerialT, 1, 2, 2, 1);
		lblSerialT.setVisible(false);
		// Label and text field for barcode
		gpMain.add(lblBarcode, 0, 3);
		gpMain.add(txtBarcode, 1, 3, 2, 1);
		txtBarcode.setVisible(false);
		gpMain.add(lblBarcodeT, 1, 3, 2, 1);
		// Label and info label for combo
		gpMain.add(lblCombo, 0, 4);
		gpMain.add(txtCombo, 1, 4, 2, 1);
		txtCombo.setVisible(false);
		gpMain.add(lblComboT, 1, 4, 2, 1);

		gpMain.add(btnCheckForExisting, 0, 5, 3, 1);
		GridPane.setHalignment(btnCheckForExisting, HPos.CENTER);

		gpButtons.add(btnUnlock, 0, 0);
		btnUnlock.setDisable(true);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnCheckForExisting.setOnAction(e -> checkForExisting());

		btnUnlock.setOnAction(e -> unlock());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));

		focus = txtSerial;
		enterBtn = btnCheckForExisting;
	}

	private void checkForExisting() {
		lblError.setText("");

		lock = null;
		if(rbSerial.isSelected()) {
			try {
				lock = Main.searchSerial(Integer.parseInt(txtSerial.getText()), false);
			} catch (NumberFormatException e) {
				lblError.setText("Check that serial is an integer.");
			}
		} else if(rbBarcode.isSelected()) {
			try {
				lock = Main.searchBarcode(Integer.parseInt(txtBarcode.getText()));
			} catch (NumberFormatException e) {
				lblError.setText("Check that barcode is an integer.");
			}
		}
		
		if(rbSerial.isSelected() || rbBarcode.isSelected()) {
			// Checks that lock exists
			if (lock == null) {
				lblError.setText("Lock not found in database.");
			} else {
				lblSerialT.setText("" + lock.getSerial());
				lblBarcodeT.setText("" + lock.getBarcode());
				lblComboT.setText(lock.getCombo());

				combo = lock.getCombo();
				
				btnCheckForExisting.setDisable(true);

				btnUnlock.setDisable(false);
			}
		}
	}

	private void unlock() {
		 if(rbCombonation.isSelected()) {
				try {
					if(txtCombo.getText().length() != 8) {
						lblError.setText("Check that barcode is in XX-XX-XX.");
					} else {
						String[] comboNums = txtCombo.getText().split("-");
						for(String temp : comboNums) {
							int num = Integer.parseInt(temp);
							if(num < 0 || num > 40) {
									throw new Exception();
							}
						}
						
						combo = txtCombo.getText();


						arduinoControl.Arduino.open(combo);
					}
				} catch (Exception e) {
					lblError.setText("Check that barcode is in XX-XX-XX.");
				}
			} else {
		arduinoControl.Arduino.open(combo);
			}
	}

	// Clears form
	private void clear() {
		txtSerial.setText("");
		lblSerialT.setText("-");
		txtBarcode.setText("");
		lblBarcodeT.setText("-");
		txtCombo.setText("");
		lblComboT.setText("-");
		
		btnCheckForExisting.setDisable(false);
		btnUnlock.setDisable(true);
	}

	// Changed visibility to show serial text field and hide barcode, combo text field
	private void setSerialMode() {
		clear();
		txtBarcode.setVisible(false);
		txtBarcode.setText("");
		lblBarcodeT.setVisible(true);
		txtSerial.setVisible(true);
		lblSerialT.setVisible(false);
		lblComboT.setVisible(true);
		txtCombo.setVisible(false);
		btnCheckForExisting.setVisible(true);
		btnCheckForExisting.setDisable(false);

		txtSerial.requestFocus();
	}

	// Changed visibility to show barcode text field and hide serial,combo text field
	private void setBarcodeMode() {
		clear();
		txtSerial.setVisible(false);
		txtSerial.setText("");
		lblSerialT.setVisible(true);
		txtBarcode.setVisible(true);
		lblBarcodeT.setVisible(false);
		lblComboT.setVisible(true);
		txtCombo.setVisible(false);
		btnCheckForExisting.setVisible(true);
		
		btnCheckForExisting.setDisable(false);
		btnUnlock.setDisable(true);

		txtBarcode.requestFocus();
	}

	// Changed visibility to show combo text field and hide serial, barcode text field
	private void setComboMode() {
		clear();
		txtCombo.setVisible(true);
		txtCombo.setText("");
		lblComboT.setVisible(false);
		txtSerial.setVisible(false);
		lblSerialT.setVisible(true);
		txtBarcode.setVisible(false);
		lblBarcodeT.setVisible(true);
		
		btnCheckForExisting.setVisible(false);
		btnUnlock.setDisable(false);

		txtCombo.requestFocus();
	}
}
