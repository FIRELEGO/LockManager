package wip;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListGUI extends GUI {
	private TableView<Lock> tvLocks = new TableView<Lock>();
	private Button btnBack = new Button("Back");
	
	public ListGUI() {
		super(600, 800, "All Info");
		
		TableColumn<Lock, Integer> tcSerial = new TableColumn<Lock, Integer>("Serial");
		TableColumn<Lock, String> tcCombo = new TableColumn<Lock, String>("Combo");
		TableColumn<Lock, Integer> tcBarcode = new TableColumn<Lock, Integer>("Barcode");
		TableColumn<Lock, Integer> tcYearAdded = new TableColumn<Lock, Integer>("Year Added");
		TableColumn<Lock, Integer> tcYearLastUsed = new TableColumn<Lock, Integer>("Year Last Used");
		TableColumn<Lock, Integer> tcTotalUses = new TableColumn<Lock, Integer>("Total Uses");

		tcSerial.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("serial"));
		tcCombo.setCellValueFactory(new PropertyValueFactory<Lock, String>("combo"));
		tcBarcode.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("barcode"));
		tcYearAdded.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("yearAdded"));
		tcYearLastUsed.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("yearLastUsed"));
		tcTotalUses.setCellValueFactory(new PropertyValueFactory<Lock, Integer>("totalUses"));

		tvLocks.getColumns().add(tcSerial);
		tvLocks.getColumns().add(tcCombo);
		tvLocks.getColumns().add(tcBarcode);
		tvLocks.getColumns().add(tcYearAdded);
		tvLocks.getColumns().add(tcYearLastUsed);
		tvLocks.getColumns().add(tcTotalUses);
		
		Lock[] lockArray = Main.getLocks("");
		ObservableList<Lock> locks = FXCollections.observableArrayList();
		
		for(int i = 0; i < lockArray.length; i++) {
			locks.add(lockArray[i]);
		}
		
		tvLocks.setItems(locks);
		
		gpMain.add(tvLocks, 0, 0);
		
		btnBack.setOnAction(e -> Main.setStage("Home"));
		gpButtons.add(btnBack, 0, 0);
	}
}
