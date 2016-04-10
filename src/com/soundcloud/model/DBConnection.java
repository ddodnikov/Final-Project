package com.soundcloud.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String DB_PASSWORD = "^itta1ent$12%_3";
	private static final String DB_USERNAME = "ittalents";
	private static final String DB_URL = "jdbc:mysql://212.50.89.157:3306/soundcloud";

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

	private static DBConnection dbinstance = null;
	private static Connection connection = null;
	
	private DBConnection() {
		try {
			Class.forName(DB_DRIVER);
			DBConnection.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DBConnection getDBInstance() {
		synchronized (DBConnection.class) {
			if (DBConnection.dbinstance == null) {
				DBConnection.dbinstance = new DBConnection();
			}
		}
		return DBConnection.dbinstance;
	}
	
	public Connection getConnection() {
		return connection;
	}

}
