package wip;

public class Lock {
	private int serial = -1;
	private String combo = "null";
	private int barcode = -1;
	private int yearAdded = -1;
	private int yearLastUsed = -1;
	private int totalUses = -1;

	public Lock(int serial, String combo, int barcode, int yearAdded, int yearLastUsed, int totalUses) {
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
	
	public void setYearAdded(int yearAdded) {
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

	public int getYearAdded() {
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
