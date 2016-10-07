package wip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lock {
	private int serial = -1;
	private String combo = "null";
	private int barcode = -1;
	private String dateAdded = "null";
	private int yearLastUsed = -1;
	
	public Lock(int serial, String combo, int barcode, String dateAdded, int yearLastUsed) {
		super();
		this.serial = serial;
		this.combo = combo;
		this.barcode = barcode;
		this.dateAdded = dateAdded;
		this.yearLastUsed = yearLastUsed;
	}
	
	public Lock(int serial, String combo) {
		super();
		this.serial = serial;
		this.combo = combo;
	}
	
	public void setDateAdded(String date) {
		dateAdded = date;
	}
	
	public int getSerial() {
		return serial;
	}

	public String getCombo() {
		return combo;
	}

	public int getBarcode() {
		return barcode;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public int getYearLastUsed() {
		return yearLastUsed;
	}

	public String toString() {
		return serial + ", " + combo + ", " + barcode + ", " + dateAdded + ", " + yearLastUsed;
	}
}
