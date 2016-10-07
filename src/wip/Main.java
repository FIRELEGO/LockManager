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

public class Main {
	// DEBUG remove from final
	public static boolean noDB = false;

	private static DBConnectionManager db;
	public static final String VERSION = "0.1.0";

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
			ret = new Lock(1, "no DB", 1, "no DB", 1);
		} else {
			ret = db.searchSerialNew(serial);
		}

		if(ret == null && checkOld) {
			ret = db.searchSerialOld(serial);
		}
		return ret;
	}

	public static void editBarcode(int serial, int barcode) {
		db.editBarcode(serial, barcode);
	}
	
	public static void addLock(int serial, String combo, int barcode, String dateAdded) {
		db.addLock(serial, combo, barcode, dateAdded);
	}

	public static String getCurDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void exit() {
		//		save(); TODO

		Platform.exit();
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
