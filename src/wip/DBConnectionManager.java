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
	private static final String DB_COMP_NAME = "localhost";
//	private static final String DB_COMP_NAME = "localhost";
	
	private static DBConnectionManager db = null;
	private static String dbURL = "jdbc:sqlserver://" + DB_COMP_NAME + "\\SQLEXPRESS;databaseName=Suncoast;";
	private static String oldDbURL = "jdbc:sqlserver://" + DB_COMP_NAME + "\\SQLEXPRESS;databaseName=OldSuncoast;";
	
	private static final String user = "sa";
	private static final String pass = "Suncoast$1";
	private Connection conn;
	private Connection oldConn;
	private PreparedStatement psFindOldBySerial;
	private PreparedStatement psFindOldByCombo;
	private PreparedStatement psFindNewBySerial;
	private PreparedStatement psFindNewByCombo;
	private PreparedStatement psEditBarcode;
	private PreparedStatement psAddLock;

	private DBConnectionManager() throws ClassNotFoundException, SQLException {
		// Set up database connection
		DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		// Use this if passing in login and password
		conn = DriverManager.getConnection(dbURL, user, pass);
		oldConn = DriverManager.getConnection(oldDbURL, user, pass);

		// Use this if using windows authentication (integratedSecurity=true)
		// conn = DriverManager.getConnection(dbURL);

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
			psFindOldBySerial = oldConn.prepareStatement("SELECT * FROM \"Locks\" WHERE lock_serial_number=?;");
			psFindOldByCombo = oldConn.prepareStatement("SELECT * FROM \"Locks\" WHERE combo=?;");
			psFindNewBySerial = conn.prepareStatement("SELECT * FROM \"Lock\" WHERE Serial=?;");
			psFindNewByCombo = conn.prepareStatement("SELECT * FROM \"Lock\" WHERE Combo=?;");
			psEditBarcode = conn.prepareStatement("UPDATE Lock SET Barcode=? WHERE Serial=?;");
			psAddLock = conn.prepareStatement("INSERT INTO Lock VALUES (?, ?, ?, ?, 3000);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Lock searchSerialNew(int serial) {
		Lock ret = null;
		
		try {
		psFindNewBySerial.setInt(1, serial);
		
		ResultSet rs = psFindNewBySerial.executeQuery();
			while(rs.next()) {
				ret = new Lock(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return ret;
	}
	
	public Lock searchSerialOld(int serial) {
		Lock ret = null;
		
		try {
		psFindOldBySerial.setInt(1, serial);
		
		ResultSet rs = psFindOldBySerial.executeQuery();
			while(rs.next()) {
				ret = new Lock(rs.getInt(1), rs.getString(2));
			}
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
	
	public void addLock(int serial, String combo, int barcode, String dateAdded) {
		try {
			psAddLock.setInt(1, serial);
			psAddLock.setString(2, combo);
			psAddLock.setInt(3, barcode);
			psAddLock.setString(4, dateAdded);
			
			psAddLock.execute();
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
