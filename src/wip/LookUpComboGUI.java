package wip;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LookUpComboGUI extends GUI {
	private Label lblCombo = new Label("Combo: ");
	private TextField txtCombo = new TextField();
	private TableView<Lock> tvLocks = new TableView<Lock>();

	private Button btnSearch = new MyButton("Search");
	private Button btnBack = new MyButton("Back");
	
	public LookUpComboGUI() {
		super(560, 600, "Look Up by Combo");

		// Label and text field for combo
		gpMain.add(lblCombo, 0, 0);
		gpMain.add(txtCombo, 1, 0);

		// Sets up all the columns of the table
		TableColumn<Lock, Integer> tcSerial = new TableColumn<Lock, Integer>("Serial");
		TableColumn<Lock, String> tcCombo = new TableColumn<Lock, String>("Combo");
		TableColumn<Lock, Integer> tcBarcode = new TableColumn<Lock, Integer>("Barcode");
		TableColumn<Lock, Integer> tcYearAdded = new TableColumn<Lock, Integer>("Year Added");
		TableColumn<Lock, Integer> tcYearLastUsed = new TableColumn<Lock, Integer>("Year Last Used");
		TableColumn<Lock, Integer> tcTotalUses = new TableColumn<Lock, Integer>("Total Uses");
		TableColumn<Lock, String> tcAssignedLocker = new TableColumn<Lock, String>("Assigned Lock");

		// Sets up column values
		tcSerial.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("serial"));
		tcCombo.setCellValueFactory(new PropertyValueFactory<Lock, String>("combo"));
		tcBarcode.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("barcode"));
		tcYearAdded.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("yearAdded"));
		tcYearLastUsed.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("yearLastUsed"));
		tcTotalUses.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("totalUses"));
		tcAssignedLocker.setCellValueFactory(new PropertyValueFactory<Lock, String>("assignedLocker"));

		tvLocks.getColumns().add(tcSerial);
		tvLocks.getColumns().add(tcCombo);
		tvLocks.getColumns().add(tcBarcode);
		tvLocks.getColumns().add(tcYearAdded);
		tvLocks.getColumns().add(tcYearLastUsed);
		tvLocks.getColumns().add(tcTotalUses);
		tvLocks.getColumns().add(tcAssignedLocker);

		gpMain.add(tvLocks, 0, 1, 2, 1);
		
		btnSearch.setOnAction(e -> search());
		btnBack.setOnAction(e -> Main.setStage("LookUp"));
		
		gpButtons.add(btnSearch, 0, 0);
		gpButtons.add(btnBack, 1, 0);
	}

	private void search() {
		lblError.setText("");

		String combo = txtCombo.getText();
		
		// Looks up all locks with provided combo
		try {
			if(combo.length() != 8) {
				lblError.setText("Make sure combo is in xx-xx-xx form.");
			} else {
				String[] comboNums = combo.split("-");
				for(String temp : comboNums) {
					Integer.parseInt(temp);
				}
				Main.log("Looked up all with the combo " + combo);
				tvLocks.setItems(Main.searchCombo(combo, true)); // TODO allow conversion from old to new and reconfigure for observable list. (No reseting the items every time, just mod the list)
			}
		} catch (NumberFormatException e) {
			lblError.setText("Make sure the combo is in xx-xx-xx!");
		}
	}
}
