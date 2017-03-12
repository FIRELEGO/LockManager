/* Nicholas Signori
 * Lock Manager
 * Start: 9/17/16
 */
package wip;
	
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/* TODO features
 * What user can do based on role
 * Fix complaint review system
 */

public class Main {
	// TODO remove from final
	public static boolean homeDB = true;
	// Object of the user who is logged in
	public static Account user;

	public static final String VERSION = "0.9.0";
//	public static final String LABEL_FONT = "Bauhaus 93";
	public static final String LABEL_FONT = "Constantia";

	private static DBConnectionManager db;

	public static void main(String[] args) {
		// TODO remove from final
		// Determines whether I am at home or school and sets DB URL accordingly
		String hostname = "Unknown";
		try	{
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
			homeDB = !hostname.substring(0, 4).equals("0151");
			System.out.println((homeDB ? "Home" : "School") + " DB used");
		} catch (UnknownHostException ex) {
			System.out.println("Hostname can not be resolved");
		}

		db = DBConnectionManager.getInstance();

		Application.launch(SetUp.class, args);
	}

	// Called from GUIs to change the screen to a new GUI. Usually triggered by button press
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
			case "DeleteLock":
				SetUp.setScene(new DeleteGUI());
				break;
			default:
				System.out.println("setStage() no case: " + key);
				break;
		}	
	}

	// Finds Serial in database. Can also search old DB for locks (Can search old DB)
	public static Lock searchSerial(int serial, boolean checkOld) {
		Lock ret;

		ret = db.searchSerialNew(serial);

		if(ret == null && checkOld) {
			ret = db.searchSerialOld(serial);
		}
		return ret;
	}

	// Finds Barcode in database. Can also search old DB for locks
	public static Lock searchBarcode(int barcode) {
		Lock ret;

		ret = db.searchBarcode(barcode);

		return ret;
	}

	// Change the barcode on a lock with a given serial
	public static void editBarcode(int serial, int barcode) {
		db.editBarcode(serial, barcode);
	}

	// Change the uses on a lock with a given serial
	public static void setUses(int serial, int uses) {
		db.setUses(serial, uses);
	}

	// Add a lock to the new DB
	public static void addLock(int serial, String combo, int barcode, int yearAdded) {
		db.addLock(serial, combo, barcode, yearAdded);
	}

	// Assign a lock to a locker and update relevant lock info
	public static void assignLocker(int serial, String lockerNum, int curYear, int curUses) {
		db.assignLocker(serial, lockerNum, curYear, curUses);
	}

	// Gets the locker assign to a lock
	public static String getAssignedLocker(int serial) {
		return db.getAssignedLocker(serial);
	}

	// Gets the current year
	public static int getCurYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return Integer.parseInt(dateFormat.format(date));
	}

	// Gets an Observable list of locks given a combo (Can check old DB)
	public static ObservableList<Lock> searchCombo(String combo, boolean checkOld) {
		return db.searchCombo(combo, checkOld);
	}

	// Method called to exit the program
	public static void exit() {
		// Don't log an exit if no one logged in
		if(user != null) {
			Main.log("Logged off.");
		}
		System.exit(0);
	}

	// Removes locks which haven't been used in over 10 years
	public static void purgeLocks() {
		// Confirmation that user wants to purge
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Purge Locks");
		alert.setHeaderText("Purge old locks from database?");
		alert.setContentText("Are you sure you want to delete all lock 10 years old or older?\nCick \"Ok\" to continue with this operation or \"Cancel\" to cancel and keep all locks in database.");

		alert.showAndWait();

		if(alert.getResult() == ButtonType.OK) {
			// User says ok
			Main.log("All locks lasted used before " + (getCurYear() - 10) + " have been purged.");
			db.purgeLocks(getCurYear());

			Alert alertDone = new Alert(AlertType.INFORMATION);
			alertDone.setTitle("Purge Locks");
			alertDone.setHeaderText("Old locks purged.");

			alertDone.showAndWait();
		} else {
			// User says cancel
			Alert alertCanceled = new Alert(AlertType.INFORMATION);
			alertCanceled.setTitle("Purge Locks");
			alertCanceled.setHeaderText("Purge canceled.");

			alertCanceled.showAndWait();
		}
	}

	// Deletes all locker assignments
	public static void releaseAllLockers() {
		// Confirmation that user wants to release all lockers
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Release All Locks");
		alert.setHeaderText("Release all lockers into circulation.");
		alert.setContentText("Are you sure you want to remove all locks from their assigned locker?\nCick \"Ok\" to continue with this operation or \"Cancel\" to cancel and keep all locks in database.");

		alert.showAndWait();

		if(alert.getResult() == ButtonType.OK) {
			// User says ok
			Main.log("All lockers released to circulation.");
			db.releaseLockers();

			Alert alertDone = new Alert(AlertType.INFORMATION);
			alertDone.setTitle("Release Lockers");
			alertDone.setHeaderText("All lockers released.");

			alertDone.showAndWait();
		} else {
			// User says cancel
			Alert alertCanceled = new Alert(AlertType.INFORMATION);
			alertCanceled.setTitle("Release Lockers");
			alertCanceled.setHeaderText("Locker release canceled.");

			alertCanceled.showAndWait();
		}
	}

	// Hashes string input to MD5
	public static String hash(String in) {
		String ret = "";
		MessageDigest m;

		try {
			m = MessageDigest.getInstance("MD5");
			m.update(in.getBytes(),0,in.length());
			ret = new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Hashing error");
			e.printStackTrace();
		}

		return ret;
	}

	// Gets all of the locks given certain SQL restrictions
	public static Lock[] getLocks(String res) {
		return db.getLocks(res);
	}

	// Gets all of the lockers given certain SQL restrictions
	public static String[][] getLockers(int floorStart, int floorEnd, int lockerStart, int lockerEnd) {
		return db.getLockers(floorStart, floorEnd, lockerStart, lockerEnd);
	}

	// Clears all reports made for a certain lock
	public static void deleteReportsByLock(int serial) {
		db.deleteReportsByLock(serial);
	}

	// Adds a report to the DB
	public static void addReport(int lockSerial, String priority, String progress, String report) {
		db.addReport(lockSerial, priority, progress, report);
	}

	// Gets reports from DB
	public static ObservableList<Report> getReports() {
		ObservableList<Report> reports = FXCollections.observableArrayList();
		ArrayList<Report> data = db.getReports();

		for(int i = 0; i < data.size(); i++) {
					reports.add(data.get(i));
		}

		return reports;
	}

	// Allows to edit lock information in DB
	public static void changeLock(Lock oldLock, Lock newLock) {
		db.changeLock(oldLock, newLock);
	}

	// Removes a lock assignment form a locker
	public static void clearAssignment(int serial) {
		db.clearAssignment(serial);
	}

	// Removes Lock from DB
	public static void deleteLock(int serial) {
		db.deleteLock(serial);
	}

	// Checks if lock or locker already has assignment
	public static String[][] checkAssignment(String lockerNum, int serial) {
		return db.checkAssignment(lockerNum, serial);
	}

	// Gets all the reports for a lock
	public static ArrayList<Report> getReportsForLock(int serial) {
		return db.getReportsForLock(serial);
	}

	// Gets user from DB
	public static Account getUser(String username) {
		return db.getUser(username);
	}

	// Logs event into DB
	public static void log(String info) {
		db.log(user.getUsername(), info);
	}
}
