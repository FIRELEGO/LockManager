package wip;

import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class LookUpGUI extends GUI {
	private ToggleGroup tgSelector = new ToggleGroup();
	private RadioButton rbSerial = new RadioButton("Serial");
	private RadioButton rbBarcode = new RadioButton("Barcode");
	private Label lblSerial = new Label("Serial: ");
	private TextField txtSerial = new TextField("");
	private Label lblBarcode = new Label("Barcode: ");
	private TextField txtBarcode = new TextField("");
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

		gpMain.add(rbSerial, 0, 0);
		rbSerial.setSelected(true);
		gpMain.add(rbBarcode, 1, 0);
		gpMain.add(lblSerial, 0, 1);
		gpMain.add(txtSerial, 1, 1);
		gpMain.add(lblBarcode, 0, 2);
		gpMain.add(txtBarcode, 1, 2);
		txtBarcode.setDisable(true);
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

		gpButtons.add(btnCheckInfo, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

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
			lblComboT.setText(lock.getCombo());
			lblYearAddedT.setText(lock.getYearAdded());
			lblYearUsedT.setText("" + (lock.getYearLastUsed() == 3000 ? "-" : lock.getYearLastUsed()));
			lblTotalUsesT.setText("" + lock.getTotalUses());
			lblAssignedLockerT.setText(Main.getAssignedLocker(lock.getSerial()));
		}
	}

	private void clear() {
		rbSerial.setSelected(true);
		txtSerial.setText("");
		txtSerial.setDisable(false);
		txtBarcode.setText("");
		txtBarcode.setDisable(true);
		lblComboT.setText("-");
		lblYearAddedT.setText("-");
		lblYearUsedT.setText("-");
		lblTotalUsesT.setText("-");
		lblAssignedLockerT.setText("-");
	}

	private void setSerialMode() {
		txtBarcode.setDisable(true);
		txtBarcode.setText("");
		txtSerial.setDisable(false);
	}

	private void setBarcodeMode() {
		txtSerial.setDisable(true);
		txtSerial.setText("");
		txtBarcode.setDisable(false);
	}
}
