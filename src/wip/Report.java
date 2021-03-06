package wip;

public class Report {
	private int reportId = 0;
	private int serial = 0;
	private String date = "";
	private String priority = "";
	private String complaint = "";
	
	public Report(int reportId, int serial, String priority, String date, String complaint) {
		this.reportId = reportId;
		this.priority = priority;
		this.serial = serial;
		this.complaint = complaint;
		this.date = date;
	}

	public int getReportId() {
		return reportId;
	}
	
	public String getPriority() {
		return priority;
	}

	public int getSerial() {
		return serial;
	}

	public String getComplaint() {
		return complaint;
	}

	public String getDate() {
		return date;
	}
}
