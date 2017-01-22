/* Nicholas Signori
 * Lock Manager
 * Start: 9/17/16
 */
package wip;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Main {
	// DEBUG remove from final
	public static boolean homeDB = true;
	
	public static final String VERSION = "0.6.0";

	private static DBConnectionManager db;
	private static HashMap<String, String> hmSettings = new HashMap<String, String>();

	public static void main(String[] args) {
		db = DBConnectionManager.getInstance();
		initialize();

		Application.launch(SetUp.class, args);
	}

	public static void setStage(String key) {
		switch(key) {
			case "Home":
				SetUp.setScene(new HomeGUI());
				break;
			case "LookUp":
				SetUp.setScene(new LookUpGUI());
				break;
			case "AddLock":
				SetUp.setScene(new AddLockGUI());
				break;
			case "AssignLocker":
				SetUp.setScene(new AssignLockerGUI());
				break;
			case "AssignBarcode":
				SetUp.setScene(new AssignBarcodeGUI());
				break;
			case "LookUpCombo":
				SetUp.setScene(new LookUpComboGUI());
				break;
			case "Generate":
				SetUp.setScene(new GenerateGUI());
				break;
			case "List":
				SetUp.setScene(new ListGUI());
				break;
			case "ViewReports":
				SetUp.setScene(new ViewReportsGUI());
				break;
			case "ReportLock":
				SetUp.setScene(new ReportGUI());
				break;
			case "EditLock":
				SetUp.setScene(new EditGUI());
				break;
			default:
				System.out.println("setStage() no case: " + key);
				break;
		}	
	}

	public static Lock searchSerial(int serial, boolean checkOld) {
		Lock ret;

		ret = db.searchSerialNew(serial);

		if(ret == null && checkOld) {
			ret = db.searchSerialOld(serial);
		}
		return ret;
	}


	public static Lock searchBarcode(int barcode) {
		Lock ret;

		ret = db.searchBarcode(barcode);

		return ret;
	}

	public static void editBarcode(int serial, int barcode) {
		db.editBarcode(serial, barcode);
	}

	public static void setUses(int serial, int uses) {
		db.setUses(serial, uses);
	}

	public static void addLock(int serial, String combo, int barcode, int yearAdded) {
		db.addLock(serial, combo, barcode, yearAdded);
	}

	public static void assignLocker(int serial, String lockerNum, int curYear, int curUses) {
		db.assignLocker(serial, lockerNum, curYear, curUses);
	}

	public static String getAssignedLocker(int serial) {
		return db.getAssignedLocker(serial);
	}

	public static int getCurYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return Integer.parseInt(dateFormat.format(date));
	}

	public static ObservableList<Lock> searchCombo(String combo, boolean checkOld) {
		return db.searchCombo(combo, checkOld);
	}

	public static void exit() {
		//		save(); TODO

		Platform.exit();
	}

	public static void purgeLocks() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Purge Locks");
		alert.setHeaderText("Purge old locks from database?");
		alert.setContentText("Are you sure you want to delete all lock 10 years old or older?\nCick \"Ok\" to continue with this operation or \"Cancel\" to cancel and keep all locks in database.");

		alert.showAndWait();

		if(alert.getResult() == ButtonType.OK) {
			db.purgeLocks(getCurYear());

			Alert alertDone = new Alert(AlertType.INFORMATION);
			alertDone.setTitle("Purge Locks");
			alertDone.setHeaderText("Old locks purged.");

			alertDone.showAndWait();
		} else {
			Alert alertCanceled = new Alert(AlertType.INFORMATION);
			alertCanceled.setTitle("Purge Locks");
			alertCanceled.setHeaderText("Purge canceled.");

			alertCanceled.showAndWait();
		}
	}

	private static void initialize() {
		try {
			Scanner scanTxt = new Scanner(new File("./res/settings.ini"));

			while (scanTxt.hasNext()) {
				String[] temp = scanTxt.nextLine().split(",");
				setSetting(temp[0], temp[1]);
			}

			scanTxt.close();
		} catch (FileNotFoundException e) {
			System.err.println("ini file not found.");
			e.printStackTrace();
		}
	}

	public static String getSetting(String settingName) {
		return hmSettings.get(settingName);
	}

	public static String setSetting(String settingName, String value) {
		return hmSettings.put(settingName, value);
	}

	public static Lock[] getLocks(String res) {
		return db.getLocks(res);
	}

	public static String[][] getLocker(String res) {
		return db.getLockers(res);
	}

	public static int addReport(int lockSerial, String priority, String progress, String date) {
		return db.addReport(lockSerial, priority, progress, date);
	}

	public static ObservableList<Report> getReports() {
		ObservableList<Report> reports = FXCollections.observableArrayList();
		Object[][] data = db.getReports();
		ArrayList<String[]> txtParts = new ArrayList<String[]>();

		Scanner scanTxt;
		try {
			scanTxt = new Scanner(new File("res/reports.csv"));
			while(scanTxt.hasNextLine()) {
				String[] line = scanTxt.nextLine().split(",");
				txtParts.add(new String[]{line[0], line[1]});
			}
			scanTxt.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < txtParts.size(); j++) {
				if(txtParts.get(j)[0].equals("" +data[i][0])) {
					reports.add(new Report((int) data[i][0], (int) data[i][1], (String) data[i][2], (String) data[i][3], (String) data[i][4], txtParts.get(i)[1]));
				}
			}
		}

		return reports;
	}

	public static void changeLock(Lock oldLock, Lock newLock) {
		db.changeLock(oldLock, newLock);
	}
}
