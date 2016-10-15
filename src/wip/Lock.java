package wip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lock {
	private int serial = -1;
	private String combo = "null";
	private int barcode = -1;
	private String yearAdded = "null";
	private int yearLastUsed = -1;
	private int totalUses = -1;

	public Lock(int serial, String combo, int barcode, String yearAdded, int yearLastUsed, int totalUses) {
		super();
		this.serial = serial;
		this.combo = combo;
		this.barcode = barcode;
		this.yearAdded = yearAdded;
		this.yearLastUsed = yearLastUsed;
		this.totalUses = totalUses;
	}
	
	public Lock(int serial, String combo) {
		super();
		this.serial = serial;
		this.combo = combo;
	}
	
	public void setYearAdded(String yearAdded) {
		this.yearAdded = yearAdded;
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

	public String getYearAdded() {
		return yearAdded;
	}

	public int getYearLastUsed() {
		return yearLastUsed;
	}
	
	public int getTotalUses() {
		return totalUses;
	}

	public String toString() {
		return serial + ", " + combo + ", " + barcode + ", " + yearAdded + ", " + yearLastUsed;
	}
}