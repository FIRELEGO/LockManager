package wip;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class LookUpGUI extends GUI {
	private Button btnLookUpCombo = new MyButton("Look Up by Combo");
	
	private ToggleGroup tgSelector = new ToggleGroup();
	private RadioButton rbSerial = new RadioButton("Serial");
	private RadioButton rbBarcode = new RadioButton("Barcode");
	private Label lblSerial = new Label("Serial: ");
	private TextField txtSerial = new TextField("");
	private Label lblSerialT = new Label("-");
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
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

	private Button btnCheckInfo = new MyButton("Check Info");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public LookUpGUI() {
		super(500, 450, "Look Up Lock");

		rbSerial.setToggleGroup(tgSelector);
		rbBarcode.setToggleGroup(tgSelector);
		rbSerial.setOnAction(e -> setSerialMode());
		rbBarcode.setOnAction(e -> setBarcodeMode());

		gpMain.add(btnLookUpCombo, 0, 0, 2, 1);
		GridPane.setHalignment(btnLookUpCombo, HPos.CENTER);
		
		gpMain.add(rbSerial, 0, 1);
		rbSerial.setSelected(true);
		gpMain.add(rbBarcode, 1, 1);
		gpMain.add(lblSerial, 0, 2);
		gpMain.add(txtSerial, 1, 2);
		gpMain.add(lblSerialT, 1, 2);
		lblSerialT.setVisible(false);
		gpMain.add(lblBarcode, 0, 3);
		gpMain.add(txtBarcode, 1, 3);
		txtBarcode.setVisible(false);
		gpMain.add(lblBarcodeT, 1, 3);
		gpMain.add(lblCombo, 0, 4);
		gpMain.add(lblComboT, 1, 4);
		gpMain.add(lblYearAdded, 0, 5);
		gpMain.add(lblYearAddedT, 1, 5);
		gpMain.add(lblYearUsed, 0, 6);
		gpMain.add(lblYearUsedT, 1, 6);
		gpMain.add(lblTotalUses, 0, 7);
		gpMain.add(lblTotalUsesT, 1, 7);
		gpMain.add(lblAssignedLocker, 0, 8);
		gpMain.add(lblAssignedLockerT, 1, 8);

		gpButtons.add(btnCheckInfo, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnLookUpCombo.setOnAction(e -> Main.setStage("LookUpCombo"));
		
		btnCheckInfo.setOnAction(e -> lookUp());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
	}

	private void lookUp() {
		lblError.setText("");
		boolean modeSerial = rbSerial.isSelected();

		Lock lock = null;
		if(modeSerial) {
			try {
				lock = Main.searchSerial(Integer.parseInt(txtSerial.getText()), false);
			} catch (NumberFormatException e) {
				lblError.setText("Check that serial is an integer.");
			}
		} else {
			try {
				lock = Main.searchBarcode(Integer.parseInt(txtBarcode.getText()));
			} catch (NumberFormatException e) {
				lblError.setText("Check that barcode is an integer.");
			}
		}

		if (lock == null) {
			lblError.setText("Lock not found in database.");
		} else {
			lblSerialT.setText("" + lock.getSerial());
			lblBarcodeT.setText("" + lock.getBarcode());
			lblComboT.setText(lock.getCombo());
			lblYearAddedT.setText("" + lock.getYearAdded());
			lblYearUsedT.setText("" + (lock.getYearLastUsed() == 3000 ? "-" : lock.getYearLastUsed()));
			lblTotalUsesT.setText("" + lock.getTotalUses());
			lblAssignedLockerT.setText(Main.getAssignedLocker(lock.getSerial()));
		}
	}

	private void clear() {
		txtSerial.setText("");
		lblSerialT.setText("-");
		txtBarcode.setText("");
		lblBarcodeT.setText("-");
		lblComboT.setText("-");
		lblYearAddedT.setText("-");
		lblYearUsedT.setText("-");
		lblTotalUsesT.setText("-");
		lblAssignedLockerT.setText("-");
	}

	private void setSerialMode() {
		clear();
		txtBarcode.setVisible(false);
		txtBarcode.setText("");
		lblBarcodeT.setVisible(true);
		txtSerial.setVisible(true);
		lblSerialT.setVisible(false);
	}

	private void setBarcodeMode() {
		clear();
		txtSerial.setVisible(false);
		txtSerial.setText("");
		lblSerialT.setVisible(true);
		txtBarcode.setVisible(true);
		lblBarcodeT.setVisible(false);
	}
}
