package wip;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class GenerateGUI extends GUI {
	private ToggleGroup tgReportType = new ToggleGroup();
	private RadioButton rbLocker = new RadioButton("Locker");
	private RadioButton rbLock = new RadioButton("Lock");

	private CheckBox cbYearAdded = new CheckBox("Year Added");
	private CheckBox cbYearLastUsed = new CheckBox("Year Last Used");
	private CheckBox cbTotalUses = new CheckBox("Total Uses");

	private CheckBox cbLockerNum = new CheckBox("Locker Number");
	private CheckBox cbFloor = new CheckBox("Floor");

	private GridPane gpLockRestrictions = new GridPane();
	private Label lblYearAdded = new Label("Year Added: ");
	private Label lblYearAddedStart = new Label("Start: ");
	private TextField txtYearAddedStart = new MyTextField(60);
	private Label lblYearAddedEnd = new Label(" End: ");
	private TextField txtYearAddedEnd = new MyTextField(60); 
	private Label lblYearLastUsed = new Label("Year Last Used: ");
	private Label lblYearLastUsedStart = new Label("Start: ");
	private TextField txtYearLastUsedStart = new MyTextField(60);
	private Label lblYearLastUsedEnd = new Label(" End: ");
	private TextField txtYearLastUsedEnd = new MyTextField(60);
	private Label lblTotalUses = new Label("Total Uses: ");
	private Label lblTotalUsesStart = new Label("Start: ");
	private TextField txtTotalUsesStart = new MyTextField(60);
	private Label lblTotalUsesEnd = new Label(" End: ");
	private TextField txtTotalUsesEnd = new MyTextField(60);

	private GridPane gpLockerRestrictions = new GridPane();
	private Label lblLockerNum = new Label("Locker Number: ");
	private Label lblLockerNumStart = new Label("Start: ");
	private TextField txtLockerNumStart = new MyTextField(60);
	private Label lblLockerNumEnd = new Label(" End: ");
	private TextField txtLockerNumEnd = new MyTextField(60);
	private Label lblFLoor = new Label("Floor: ");
	private Label lblFloorStart = new Label("Start: ");
	private TextField txtFloorStart = new MyTextField(60);
	private Label lblFloorEnd = new Label(" End: ");
	private TextField txtFloorEnd = new MyTextField(60);
	
	private Button btnCheckRestrictions = new MyButton("Check Restriction");
	private TextField txtPath = new TextField();
	private Button btnChoose = new MyButton("Choose Other Path");

	private Button btnCreate = new MyButton("Create PDF");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");
	
	public GenerateGUI() {
		super(500, 600, "Generate Data Sheet");

		gpMain.add(new Label("Report type:"), 0, 0, 2, 1);
		
		rbLock.setToggleGroup(tgReportType);
		rbLock.setSelected(true);
		rbLocker.setToggleGroup(tgReportType);

		gpMain.add(rbLock, 0, 1, 2, 1);
		gpMain.add(new Label("    "), 0, 2);
		gpMain.add(new Label("Restrictions"), 1, 2);
		
		// Add cb and toggle function for year added
		gpMain.add(cbYearAdded, 1, 3);
		cbYearAdded.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				lblYearAdded.setVisible(cbYearAdded.isSelected());
				lblYearAddedStart.setVisible(cbYearAdded.isSelected());
				txtYearAddedStart.setVisible(cbYearAdded.isSelected());
				lblYearAddedEnd.setVisible(cbYearAdded.isSelected());
				txtYearAddedEnd.setVisible(cbYearAdded.isSelected());
				if(!cbYearAdded.isSelected()) {
					txtYearAddedStart.setText("");
					txtYearAddedEnd.setText("");
				}
			}
		});
		
		// Add cb and toggle function for year last used
		gpMain.add(cbYearLastUsed, 1, 4);
		cbYearLastUsed.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				lblYearLastUsed.setVisible(cbYearLastUsed.isSelected());
				lblYearLastUsedStart.setVisible(cbYearLastUsed.isSelected());
				txtYearLastUsedStart.setVisible(cbYearLastUsed.isSelected());
				lblYearLastUsedEnd.setVisible(cbYearLastUsed.isSelected());
				txtYearLastUsedEnd.setVisible(cbYearLastUsed.isSelected());
				if(!cbYearLastUsed.isSelected()) {
					txtYearLastUsedStart.setText("");
					txtYearLastUsedEnd.setText("");
				}
			}
		});

		// Add cb and toggle function for total uses
		gpMain.add(cbTotalUses, 1, 5);
		cbTotalUses.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				lblTotalUses.setVisible(cbTotalUses.isSelected());
				lblTotalUsesStart.setVisible(cbTotalUses.isSelected());
				txtTotalUsesStart.setVisible(cbTotalUses.isSelected());
				lblTotalUsesEnd.setVisible(cbTotalUses.isSelected());
				txtTotalUsesEnd.setVisible(cbTotalUses.isSelected());
				if(!cbTotalUses.isSelected()) {
					txtTotalUsesStart.setText("");
					txtTotalUsesEnd.setText("");
				}
			}
		});

		gpMain.add(rbLocker, 0, 6, 2, 1);
		gpMain.add(new Label("Restrictions"), 1, 7);
		gpMain.add(cbLockerNum, 1, 8);

		// Add cb and toggle function for locker number
		cbLockerNum.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				lblLockerNum.setVisible(cbLockerNum.isSelected());
				lblLockerNumStart.setVisible(cbLockerNum.isSelected());
				txtLockerNumStart.setVisible(cbLockerNum.isSelected());
				lblLockerNumEnd.setVisible(cbLockerNum.isSelected());
				txtLockerNumEnd.setVisible(cbLockerNum.isSelected());
				if(!cbLockerNum.isSelected()) {
					txtLockerNumStart.setText("");
					txtLockerNumEnd.setText("");
				}
			}
		});
		cbLockerNum.setDisable(true);

		// Add cb and toggle function for floor
		gpMain.add(cbFloor, 1, 9);
		cbFloor.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				lblFLoor.setVisible(cbFloor.isSelected());
				lblFloorStart.setVisible(cbFloor.isSelected());
				txtFloorStart.setVisible(cbFloor.isSelected());
				lblFloorEnd.setVisible(cbFloor.isSelected());
				txtFloorEnd.setVisible(cbFloor.isSelected());
				if(!cbFloor.isSelected()) {
					txtFloorStart.setText("");
					txtFloorEnd.setText("");
				}
			}
		});
		cbFloor.setDisable(true);

		gpMain.add(gpLockRestrictions, 0, 10, 2, 1);
		gpLockRestrictions.setStyle("-fx-border: 2px; -fx-border-color: black;");
		// Add year added to restrictions.
		gpLockRestrictions.add(lblYearAdded, 0, 1);
		lblYearAdded.setVisible(false);
		gpLockRestrictions.add(lblYearAddedStart, 1, 1);
		lblYearAddedStart.setVisible(false);
		gpLockRestrictions.add(txtYearAddedStart, 2, 1);
		txtYearAddedStart.setVisible(false);
		gpLockRestrictions.add(lblYearAddedEnd, 3, 1);
		lblYearAddedEnd.setVisible(false);
		gpLockRestrictions.add(txtYearAddedEnd, 4, 1);
		txtYearAddedEnd.setVisible(false);
		// Add year last used to restrictions.
		gpLockRestrictions.add(lblYearLastUsed, 0, 2);
		lblYearLastUsed.setVisible(false);
		gpLockRestrictions.add(lblYearLastUsedStart, 1, 2);
		lblYearLastUsedStart.setVisible(false);
		gpLockRestrictions.add(txtYearLastUsedStart, 2, 2);
		txtYearLastUsedStart.setVisible(false);
		gpLockRestrictions.add(lblYearLastUsedEnd, 3, 2);
		lblYearLastUsedEnd.setVisible(false);
		gpLockRestrictions.add(txtYearLastUsedEnd, 4, 2);
		txtYearLastUsedEnd.setVisible(false);
		// Add total uses to restrictions.
		gpLockRestrictions.add(lblTotalUses, 0, 3);
		lblTotalUses.setVisible(false);
		gpLockRestrictions.add(lblTotalUsesStart, 1, 3);
		lblTotalUsesStart.setVisible(false);
		gpLockRestrictions.add(txtTotalUsesStart, 2, 3);
		txtTotalUsesStart.setVisible(false);
		gpLockRestrictions.add(lblTotalUsesEnd, 3, 3);
		lblTotalUsesEnd.setVisible(false);
		gpLockRestrictions.add(txtTotalUsesEnd, 4, 3);
		txtTotalUsesEnd.setVisible(false);

		gpMain.add(gpLockerRestrictions, 0, 10, 2, 1);
		gpLockerRestrictions.setStyle("-fx-border: 2px; -fx-border-color: black;");
		// Add locker num to restrictions.
		gpLockerRestrictions.setVisible(false);
		gpLockerRestrictions.add(lblLockerNum, 0, 1);
		lblLockerNum.setVisible(false);
		gpLockerRestrictions.add(lblLockerNumStart, 1, 1);
		lblLockerNumStart.setVisible(false);
		gpLockerRestrictions.add(txtLockerNumStart, 2, 1);
		txtLockerNumStart.setVisible(false);
		gpLockerRestrictions.add(lblLockerNumEnd, 3, 1);
		lblLockerNumEnd.setVisible(false);
		gpLockerRestrictions.add(txtLockerNumEnd, 4, 1);
		txtLockerNumEnd.setVisible(false);
		// Add floor to restrictions.
		gpLockerRestrictions.add(lblFLoor, 0, 2);
		lblFLoor.setVisible(false);
		gpLockerRestrictions.add(lblFloorStart, 1, 2);
		lblFloorStart.setVisible(false);
		gpLockerRestrictions.add(txtFloorStart, 2, 2);
		txtFloorStart.setVisible(false);
		gpLockerRestrictions.add(lblFloorEnd, 3, 2);
		lblFloorEnd.setVisible(false);
		gpLockerRestrictions.add(txtFloorEnd, 4, 2);
		txtFloorEnd.setVisible(false);

		gpMain.add(btnCheckRestrictions, 0, 11, 2, 1);
		btnCheckRestrictions.setOnAction(e -> checkRestrictions());
		gpMain.add(new Label("Save as:"), 0, 12);
		gpMain.add(txtPath, 1, 12);
		txtPath.setDisable(true);
		gpMain.add(btnChoose, 2, 12);
		btnChoose.setDisable(true);
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
		Date date = new Date();
		btnChoose.setOnAction(e -> txtPath.setText(SetUp.openFC(new File(System.getProperty("user.dir")), (rbLock.isSelected() ? "Lock Report " : "Locker Report ") + dateFormat.format(date))));
		
		rbLock.setOnAction(e -> enableLockOptions());
		rbLocker.setOnAction(e -> enableLockerOptions());
		
		
		gpButtons.add(btnCreate, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);
		btnCreate.setDisable(true);
		
		btnCreate.setOnAction(e -> create());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
	}

	private void create() {
		if(rbLock.isSelected()) {
			boolean[] reses = {false, false, false};
			String res = "";
			
				reses[0] = cbYearAdded.isSelected();
				reses[1] = cbYearLastUsed.isSelected();
				reses[2] = cbTotalUses.isSelected();
			
			if(reses[0]) {
				res += " YearAdded >= " + txtYearAddedStart.getText() + " AND YearAdded <= " + txtYearAddedEnd.getText();
			}
			
			if(reses[1]) {
				if(reses[0]) {
					res += " && ";
				}
				res += " YearLastUsed >= " + txtYearLastUsedStart.getText() + " AND YearLastUsed <= " + txtYearLastUsedEnd.getText();
			}
			

			if(reses[2]) {
				if(reses[1] || reses[0]) {
					res += " && ";
				}
				res += " TotalUses >= " + txtTotalUsesStart.getText() + " AND TotalUses <= " + txtTotalUsesEnd.getText();
			}
			
			Lock[] data = Main.getLocks(res);

			DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
			Date date = new Date();
			Pdf.createLockPDF(txtPath.getText(), dateFormat.format(date), res, data);
		} else { // TODO figure our locker num and floor system
			boolean[] reses = {false, false};
			String res = "";
			
				reses[0] = cbLockerNum.isSelected();
				reses[1] = cbFloor.isSelected();
			
			if(reses[0]) {
				res += " LockerNum >= " + txtLockerNumStart.getText() + " AND LockerNum <= " + txtLockerNumEnd.getText();
			}
			
			if(reses[1]) {
				if(reses[0]) {
					res += " && ";
				}
				res += " FLoor >= " + txtFloorStart.getText() + " AND FLoor <= " + txtFloorEnd.getText();
			}
			
			String[][] data = Main.getLocker(res);

			DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
			Date date = new Date();
			Pdf.createLockerPDF(txtPath.getText(), dateFormat.format(date), res, data);
		}
	}
	
	private void enableLockOptions() {
		// Disable and hide floor options
		cbFloor.setSelected(false);
		cbFloor.setDisable(true);
		lblFLoor.setVisible(false);
		lblFloorStart.setVisible(false);
		txtFloorStart.setVisible(false);
		lblFloorEnd.setVisible(false);
		txtFloorEnd.setVisible(false);
		
		// Disable and hide locker number options
		cbLockerNum.setSelected(false);
		cbLockerNum.setDisable(true);
		lblLockerNum.setVisible(false);
		lblLockerNumStart.setVisible(false);
		txtLockerNumStart.setVisible(false);
		lblLockerNumEnd.setVisible(false);
		txtLockerNumEnd.setVisible(false);

		// Activate lock buttons
		cbYearAdded.setDisable(false);
		cbYearLastUsed.setDisable(false);
		cbTotalUses.setDisable(false);

		// Switch visible restrictions
		gpLockerRestrictions.setVisible(false);
		gpLockRestrictions.setVisible(true);
	}

	private void enableLockerOptions() {
		// Disable and hide year added options
		cbYearAdded.setSelected(false);
		cbYearAdded.setDisable(true);
		lblYearAdded.setVisible(false);
		lblYearAddedStart.setVisible(false);
		txtYearAddedStart.setVisible(false);
		lblYearAddedEnd.setVisible(false);
		txtYearAddedEnd.setVisible(false);

		// Disable and hide year last used options
		cbYearLastUsed.setSelected(false);
		cbYearLastUsed.setDisable(true);
		lblYearLastUsed.setVisible(false);
		lblYearLastUsedStart.setVisible(false);
		txtYearLastUsedStart.setVisible(false);
		lblYearLastUsedEnd.setVisible(false);
		txtYearLastUsedEnd.setVisible(false);

		// Disable and hide total use options
		cbTotalUses.setSelected(false);
		cbTotalUses.setDisable(true);
		lblTotalUses.setVisible(false);
		lblTotalUsesStart.setVisible(false);
		txtTotalUsesStart.setVisible(false);
		lblTotalUsesEnd.setVisible(false);
		txtTotalUsesEnd.setVisible(false);

		// Activate locker buttons
		cbFloor.setDisable(false);
		cbLockerNum.setDisable(false);

		// Switch visible restrictions
		gpLockRestrictions.setVisible(false);
		gpLockerRestrictions.setVisible(true);
	}
	
	private void clear() {
		txtFloorEnd.setText("");
		txtFloorStart.setText("");
		txtLockerNumEnd.setText("");
		txtLockerNumStart.setText("");
		txtTotalUsesEnd.setText("");
		txtTotalUsesStart.setText("");
		txtYearAddedEnd.setText("");
		txtYearAddedStart.setText("");
		txtYearLastUsedEnd.setText("");
		txtYearLastUsedStart.setText("");
		rbLocker.fire();
		rbLock.fire();
	}
	
	private void checkRestrictions() {
		lblError.setText("");
		boolean cont = true;
		
		if(cbYearAdded.isSelected() && Integer.parseInt(txtYearAddedStart.getText()) > Integer.parseInt(txtYearAddedEnd.getText())) {
			cont = false;
			lblError.setText("Year added restricions inproper. Start must be less than end.");
		}

		if(cbYearLastUsed.isSelected() && Integer.parseInt(txtYearLastUsedStart.getText()) > Integer.parseInt(txtYearLastUsedEnd.getText())) {
			cont = false;
			lblError.setText("Year last used restricions inproper. Start must be less than end.");
		}

		if(cbTotalUses.isSelected() && Integer.parseInt(txtTotalUsesStart.getText()) > Integer.parseInt(txtTotalUsesEnd.getText())) {
			cont = false;
			lblError.setText("Total uses restricions inproper. Start must be less than end.");
		}

		if(cbLockerNum.isSelected() && Integer.parseInt(txtLockerNumStart.getText()) > Integer.parseInt(txtLockerNumEnd.getText())) {
			cont = false;
			lblError.setText("Locker number restricions inproper. Start must be less than end.");
		}
		
		if(cbFloor.isSelected() && Integer.parseInt(txtFloorStart.getText()) > Integer.parseInt(txtFloorEnd.getText())) {
			cont = false;
			lblError.setText("Floor restricions inproper. Start must be less than end.");
		}
		
		if(cont) {
			btnCreate.setDisable(false);
			btnChoose.setDisable(false);
			txtPath.setDisable(false);
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh;mm,ss");
			Date date = new Date();
			txtPath.setText(new File(System.getProperty("user.home") + "\\Desktop\\Report " + dateFormat.format(date)).getAbsolutePath() + ".pdf");
		} else {
			btnCreate.setDisable(true);
		}
	}
}
