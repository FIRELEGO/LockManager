package wip;

public class Lock {
	private int serial = -1;
	private String combo = "null";
	private int barcode = -1;
	private int yearAdded = -1;
	private int yearLastUsed = -1;
	private int totalUses = -1;
	private String assignedLocker = "";

	public Lock(int serial, String combo, int barcode, int yearAdded, int yearLastUsed, int totalUses, String assignedLocker) {
		super();
		this.serial = serial;
		this.combo = combo;
		this.barcode = barcode;
		this.yearAdded = yearAdded;
		this.yearLastUsed = yearLastUsed;
		this.totalUses = totalUses;
		this.assignedLocker = assignedLocker;
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
	
	public String getAssignedLocker() {
		return assignedLocker;
	}
	
	public boolean equals(Lock lock) {
		return serial == lock.getSerial();
	}

	@Override
	public String toString() {
		return "Lock [serial=" + serial + ", combo=" + combo + ", barcode=" + barcode + ", yearAdded=" + yearAdded + ", yearLastUsed=" + yearLastUsed + ", totalUses=" + totalUses + ", assignedLocker=" + assignedLocker + "]";
	}
}
