/* Nicholas Signori
 * Lock Manager
 * Start: 9/17/16
 */
package wip;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Main {
	// DEBUG remove from final
	public static boolean noDB = false;
	public static boolean homeDB = false;

	private static DBConnectionManager db;
	public static final String VERSION = "0.2.0";

	public static void main(String[] args) {
		if(!noDB) {
			db = DBConnectionManager.getInstance();
			//			DBConnectionManager.info();
			//			load();
		} else {
			//TODO make noDB thing
		}

		Application.launch(SetUp.class, args);
	}

	public static void setStage(String key) {
		switch(key) {
			case "Home":
				SetUp.setScene(new HomeGUI());
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
			default:
				System.out.println("setStage() no case: " + key);
				break;
		}	
	}

	public static Lock searchSerial(int serial, boolean checkOld) {
		Lock ret;

		if(noDB) {
			ret = new Lock(1, "no DB", 1, "no DB", 1, 1);
		} else {
			ret = db.searchSerialNew(serial);
		}

		if(ret == null && checkOld) {
			ret = db.searchSerialOld(serial);
		}
		return ret;
	}
	

	public static Lock searchBarcode(int barcode) {
		Lock ret;

		if(noDB) {
			ret = new Lock(1, "no DB", 1, "no DB", 1, 1);
		} else {
			ret = db.searchBarcode(barcode);
		}

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

	public static String getCurYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return dateFormat.format(date);
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
			db.purgeLocks(Integer.parseInt(getCurYear()));
			
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

	//	public static void load() {
	//		users = db.getUsers();
	//		accts = db.getAccts();
	//	}

	//	public static void save() {
	//				db.saveUsers(users);
	//				db.saveAccts(accts);
	//
	//	}
}
