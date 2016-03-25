package com.soundcloud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO implements IUserDAO {

	private String getInitialDisplayName(String email) {
		
		StringBuilder name = new StringBuilder();
		
		int index = 0;
		while(email.charAt(index) != '@') {
			name.append(email.charAt(index));
			index++;
		}

		return name.toString();
	}
	
	@Override
	public void addUser(String email, String password) {
		
		Connection con = DBConnection.getDBInstance().getConnection();

		PreparedStatement addUser = null;
		
		try {
			addUser = con.prepareStatement("INSERT INTO users (email, password,display_name) VALUES(?,?,?);");
			addUser.setString(1, email);
			addUser.setString(2, password);
			addUser.setString(3, getInitialDisplayName(email));
			
			addUser.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	public boolean isEmailUsed(String email){

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
		
		try {
			if(!result.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
