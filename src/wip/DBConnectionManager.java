/* Nicholas Signori
 * Bank
 */
package wip;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnectionManager {
	private static final String DB_COMP_NAME = Main.homeDB ? "localhost" : "0151T3105C47541";
	// TODO OLD_DB_COMP_NAME will be independent once fully released
	private static final String OLD_DB_COMP_NAME = DB_COMP_NAME;

	private static DBConnectionManager db = null;
	private static String dbURL = "jdbc:mysql://" + DB_COMP_NAME + "/Suncoast";
	private static String oldDbURL = "jdbc:mysql://" + OLD_DB_COMP_NAME + "/OldSuncoast";

	private static final String user = "root";
	private static final String pass = "Suncoast$1";
	private Connection conn;
	private Connection oldConn;
	private PreparedStatement psFindBySerialOld;
	private PreparedStatement psFindBySerialNew;
	private PreparedStatement psFindByComboNew;
	private PreparedStatement psFindByComboOld;
	private PreparedStatement psFindByBarcode;
	private PreparedStatement psEditBarcode;
	private PreparedStatement psSetUses;
	private PreparedStatement psAssignLocker;
	private PreparedStatement psClearOldAssign;
	private PreparedStatement psAddLock;
	private PreparedStatement psSetLockUse;
	private PreparedStatement psPurgeOldLocks;
	private PreparedStatement psGetAssignedLocker;
	private PreparedStatement psGetReportLength;
	private PreparedStatement psGetReports;
	private PreparedStatement psAddReport;

	private DBConnectionManager() throws ClassNotFoundException, SQLException {
		// Set up database connection
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");

		// Use this if passing in login and password
		conn = DriverManager.getConnection(dbURL, user, pass);
		oldConn = DriverManager.getConnection(oldDbURL, user, pass);

		makePS();		
	}

	public static DBConnectionManager getInstance() {
		if (db == null) {
			try {
				db = new DBConnectionManager();
			} catch (ClassNotFoundException | SQLException e) {
				System.err.println("DB connection error");
				e.printStackTrace();
			}
		}

		return db;
	}

	public void close() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}

			if (oldConn != null && !oldConn.isClosed()) {
				oldConn.close();
			}
		} catch (SQLException e) {
			System.err.println("DB close error");
			e.printStackTrace();
		}
	}

	private Connection getConnection() {
		return conn;
	}


	private Connection getOldConnection() {
		return oldConn;
	}

	private void makePS() {
		try {
			psFindBySerialOld = oldConn.prepareStatement("SELECT * FROM oldsuncoast.locks WHERE lock_serial_number=?;");
			psFindBySerialNew = conn.prepareStatement("SELECT * FROM suncoast.lock WHERE Serial=?;");
			psFindByComboNew = conn.prepareStatement("SELECT * FROM suncoast.lock WHERE Combo=?;");
			psFindByComboOld = conn.prepareStatement("SELECT * FROM oldsuncoast.locks WHERE Combo=?;");
			psFindByBarcode = conn.prepareStatement("SELECT * FROM suncoast.lock WHERE Barcode=?;");
			psEditBarcode = conn.prepareStatement("UPDATE suncoast.lock SET Barcode=? WHERE Serial=?;");
			psSetUses = conn.prepareStatement("UPDATE suncoast.lock SET TotalUses=? WHERE Serial=?;");
			psAssignLocker = conn.prepareStatement("INSERT INTO suncoast.lockerassignment (LockerNum, Serial) VALUES (?, ?);");
			psClearOldAssign = conn.prepareStatement("DELETE FROM suncoast.lockerassignment WHERE LockerNum=? OR Serial=?;");
			psAddLock = conn.prepareStatement("INSERT INTO suncoast.lock (Serial, Combo, Barcode, YearAdded, YearLastUsed, TotalUses) VALUES (?, ?, ?, ?, 3000, 0);");
			psSetLockUse = conn.prepareStatement("UPDATE suncoast.lock SET YearLastUsed=?, TotalUses=? WHERE Serial=?;");
			psPurgeOldLocks = conn.prepareStatement("DELETE FROM suncoast.lock WHERE YearAdded<?;");
			psGetAssignedLocker = conn.prepareStatement("SELECT * FROM suncoast.lockerassignment WHERE Serial=?;");
			psGetReportLength = conn.prepareStatement("SELECT count(*) FROM suncoast.reports;");
			psGetReports = conn.prepareStatement("SELECT * FROM suncoast.reports;");
			psAddReport = conn.prepareStatement("INSERT INTO suncoast.reports (ReportID, LockSerial, Priority, Progress, Date) VALUES (?, ?, ?, ?, ?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Lock searchSerialNew(int serial) {
		Lock ret = null;

		try {
			psFindBySerialNew.setInt(1, serial);

			ResultSet rs = psFindBySerialNew.executeQuery();
			while(rs.next()) {
				ret = new Lock(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public Lock searchSerialOld(int serial) {
		Lock ret = null;

		try {
			psFindBySerialOld.setInt(1, serial);

			ResultSet rs = psFindBySerialOld.executeQuery();
			while(rs.next()) {
				ret = new Lock(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public ObservableList<Lock> searchCombo(String combo, boolean checkOld) {
		ObservableList<Lock> ret = FXCollections.observableArrayList();

		try {
			psFindByComboNew.setString(1, combo);
			ResultSet rs = psFindByComboNew.executeQuery();

			while(rs.next()) {
				ret.add(new Lock(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
			}

			psFindByComboOld.setString(1, combo);
			rs = psFindByComboOld.executeQuery();

			while(rs.next()) {
				ret.add(new Lock(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public Lock searchBarcode(int barcode) {
		Lock ret = null;

		try {
			psFindByBarcode.setInt(1, barcode);

			ResultSet rs = psFindByBarcode.executeQuery();
			while(rs.next()) {
				ret = new Lock(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public String getAssignedLocker(int serial) {
		String ret = "";

		try {
			psGetAssignedLocker.setInt(1, serial);

			ResultSet rs = psGetAssignedLocker.executeQuery();
			boolean hasResult = rs.isBeforeFirst();
			rs.next();
			ret = !hasResult ? "-" : rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public void editBarcode(int serial, int barcode) {
		try {
			psEditBarcode.setInt(1, barcode);
			psEditBarcode.setInt(2, serial);

			psEditBarcode.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setUses(int serial, int uses) {
		try {
			psSetUses.setInt(1, uses);
			psSetUses.setInt(2, serial);

			psSetUses.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addLock(int serial, String combo, int barcode, int yearAdded) {
		try {
			psAddLock.setInt(1, serial);
			psAddLock.setString(2, combo);
			psAddLock.setInt(3, barcode);
			psAddLock.setInt(4, yearAdded);

			psAddLock.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void assignLocker(int serial, String lockerNum, int curYear, int curUses) {
		try {
			psClearOldAssign.setString(1, lockerNum);
			psClearOldAssign.setInt(2, serial);

			psClearOldAssign.execute();

			psAssignLocker.setString(1, lockerNum);
			psAssignLocker.setInt(2, serial);

			psAssignLocker.execute();

			psSetLockUse.setInt(1, curYear);
			psSetLockUse.setInt(2, ++curUses);
			psSetLockUse.setInt(3, serial);

			psSetLockUse.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void purgeLocks(int curYear) {
		int newYear = curYear - 10;

		try {
			psPurgeOldLocks.setInt(1, newYear);

			psPurgeOldLocks.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void info() {
		Connection conn = getInstance().getConnection();
		Connection oldConn = getInstance().getOldConnection();

		try {
			DatabaseMetaData dm = conn.getMetaData();
			System.out.println("============================================================\nDriver name: " + dm.getDriverName() + "\nDriver version: " + dm.getDriverVersion() + "\nProduct name: " + dm.getDatabaseProductName() + "\nProduct version: " + dm.getDatabaseProductVersion() + "\n============================================================");

			DatabaseMetaData oldDm = oldConn.getMetaData();
			System.out.println("============================================================\nDriver name: " + oldDm.getDriverName() + "\nDriver version: " + oldDm.getDriverVersion() + "\nProduct name: " + oldDm.getDatabaseProductName() + "\nProduct version: " + oldDm.getDatabaseProductVersion() + "\n============================================================");

			conn.close();
		} catch (SQLException e) {
			System.err.println("DB info error");
		}
	}

	public Lock[] getLocks(String res) {
		ArrayList<Lock> locks = new ArrayList<Lock>();

		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM suncoast.lock" + (!res.equals("") ? " WHERE" + res : ""));
			while(rs.next()) {
				locks.add(new Lock(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Lock[] ret = new Lock[locks.size()];

		for(int k = 0; k < ret.length; k++) {
			ret[k] = locks.get(k);
		}

		return ret;
	}

	public String[][] getLockers(String res) {

		ArrayList<String[]> lockers = new ArrayList<String[]>();

		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM suncoast.lockerassignment" + (!res.equals("") ? " WHERE" + res : ""));
			while(rs.next()) {
				lockers.add(new String[]{rs.getString(1), "" + rs.getInt(2)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[][] ret = new String[lockers.size()][2];

		for(int k = 0; k < ret.length; k++) {
			ret[k][0] = lockers.get(k)[0];
			ret[k][1] = lockers.get(k)[1];
		}

		return ret;
	}

	private int reportLength() {
		int ret = 0;

		try {
			ResultSet rs = psGetReportLength.executeQuery();
			rs.next();

			ret = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	public int addReport(int lockSerial, String priority, String progress, String date) {
		int reportID = reportLength();
		
		try {
			psAddReport.setInt(1, reportID);
			psAddReport.setInt(2, lockSerial);
			psAddReport.setString(3, priority);
			psAddReport.setString(4, progress);
			psAddReport.setString(5, date);

			psAddReport.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reportID;
	}
	
	public Object[][] getReports() {

		ArrayList<Object[]> data = new ArrayList<Object[]>();

		try {
			ResultSet rs = psGetReports.executeQuery();
			while(rs.next()) {
				data.add(new Object[]{rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Object[][] ret = new Object[data.size()][5];

		for(int k = 0; k < ret.length; k++) {
			ret[k][0] = data.get(k)[0];
			ret[k][1] = data.get(k)[1];
			ret[k][2] = data.get(k)[2];
			ret[k][3] = data.get(k)[3];
			ret[k][4] = data.get(k)[4];
		}

		return ret;
	}
}
