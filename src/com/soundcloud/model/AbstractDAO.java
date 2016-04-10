package com.soundcloud.model;

import java.sql.Connection;

public abstract class AbstractDAO {
	
	private final Connection con = DBConnection.getDBInstance().getConnection();

	public Connection getCon() {
		return con;
	}
}
