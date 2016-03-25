package com.soundcloud.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO implements IUserDAO {

	@Override
	public void addUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmailUsed(String email) {

		Connection con = DBConnection.getDBInstance().getConnection();

		Statement hasAUser = null;
		ResultSet result = null;
		
		try {
			hasAUser = con.createStatement();
			hasAUser.executeQuery("SELECT * FROM users WHERE email = '" + email + "';");
			result = hasAUser.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(result == null) {
			return false;
		}
		
		return true;
	}

}
