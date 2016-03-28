package com.soundcloud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {

	private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
	private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";

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

		PreparedStatement hasAUser = null;
		ResultSet result = null;
		
		try {
			hasAUser = con.prepareStatement(SELECT_USER_BY_EMAIL);
			hasAUser.setString(1, email);
			hasAUser.execute();
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

	public boolean isExistingUser(String email, String password) {
		
		Connection con = DBConnection.getDBInstance().getConnection();
		
		PreparedStatement hasAUser = null;
		ResultSet result = null;
		
		try {
			hasAUser = con.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD);
			hasAUser.setString(1, email);
			hasAUser.setString(2, password);
			hasAUser.execute();
			result = hasAUser.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
