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
import java.util.ArrayList;
import java.util.HashMap;

public class DBConnectionManager {
	private static final String DB_COMP_NAME = Main.homeDB ? "localhost" : "0151T3105C47541";
	// OLD_DB_COMP_NAME will be independent once fully released
	private static final String OLD_DB_COMP_NAME = DB_COMP_NAME;

	private static DBConnectionManager db = null;
	private static String dbURL = "jdbc:mysql://" + DB_COMP_NAME + "/Suncoast";
	private static String oldDbURL = "jdbc:mysql://" + OLD_DB_COMP_NAME + "/OldSuncoast";

	private static final String user = "root";
	private static final String pass = "Suncoast$1";
	private Connection conn;
	private Connection oldConn;
	private PreparedStatement psFindBySerialOld;
	private PreparedStatement psFindByComboOld;
	private PreparedStatement psFindBySerialNew;
	private PreparedStatement psFindByComboNew;
	private PreparedStatement psFindByBarcode;
	private PreparedStatement psEditBarcode;
	private PreparedStatement psSetUses;
	private PreparedStatement psAssignLocker;
	private PreparedStatement psClearOldAssign;
	private PreparedStatement psAddLock;
	private PreparedStatement psSetLockUse;
	private PreparedStatement psPurgeOldLocks;
	private PreparedStatement psGetAssignedLocker;

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
			psFindByComboOld = oldConn.prepareStatement("SELECT * FROM oldsuncoast.locks WHERE combo=?;");
			psFindBySerialNew = conn.prepareStatement("SELECT * FROM suncoast.lock WHERE Serial=?;");
			psFindByComboNew = conn.prepareStatement("SELECT * FROM suncoast.lock WHERE Combo=?;");
			psFindByBarcode = conn.prepareStatement("SELECT * FROM suncoast.lock WHERE Barcode=?;");
			psEditBarcode = conn.prepareStatement("UPDATE suncoast.lock SET Barcode=? WHERE Serial=?;");
			psSetUses = conn.prepareStatement("UPDATE suncoast.lock SET TotalUses=? WHERE Serial=?;");
			psAssignLocker = conn.prepareStatement("INSERT INTO suncoast.lockerassignment (LockerNum, Serial) VALUES (?, ?);");
			psClearOldAssign = conn.prepareStatement("DELETE FROM suncoast.lockerassignment WHERE LockerNum=? OR Serial=?;");
			psAddLock = conn.prepareStatement("INSERT INTO suncoast.lock (Serial, Combo, Barcode, YearAdded, YearLastUsed, TotalUses) VALUES (?, ?, ?, ?, 3000, 0);");
			psSetLockUse = conn.prepareStatement("UPDATE suncoast.lock SET YearLastUsed=?, TotalUses=? WHERE Serial=?;");
			psPurgeOldLocks = conn.prepareStatement("DELETE FROM suncoast.lock WHERE YearAdded<?;");
			psGetAssignedLocker = conn.prepareStatement("SELECT * FROM suncoast.lockerassignment WHERE Serial=?;");
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
				ret = new Lock(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
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

	public Lock searchBarcode(int barcode) {
		Lock ret = null;

		try {
			psFindByBarcode.setInt(1, barcode);

			ResultSet rs = psFindByBarcode.executeQuery();
			while(rs.next()) {
				ret = new Lock(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
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


}
