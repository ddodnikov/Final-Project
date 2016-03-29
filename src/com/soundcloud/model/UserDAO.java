package com.soundcloud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {

	private static final String INSERT_USER = "INSERT INTO users (email, password,display_name) VALUES(?,?,?);";
	private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
	private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
	private static final String SELECT_IMAGE_BY_USER_ID = "SELECT user_img_id FROM users WHERE user_id = ?;";
	
	private static final Connection con = DBConnection.getDBInstance().getConnection();
	
	
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

		PreparedStatement addUser = null;
		
		try {
			addUser = con.prepareStatement(INSERT_USER);
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
	public User getUserById(int id) {
		User result = null;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("SELECT * FROM users WHERE user_id = ?;");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String email = rs.getString(2);
				String displayName = rs.getString(4);
				String firstName = rs.getString(5);
				String lastName = rs.getString(6);
				String city = rs.getString(7);
				String country = rs.getString(8);
				String biography = rs.getString(9);
				result = new User(email, displayName, firstName, lastName, city, country, biography);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return result;
		
	}

	@Override
	public boolean isEmailUsed(String email){

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
	
	public String getImageById(int user_id) {

		PreparedStatement selectImage = null;
		
		ResultSet result = null;
		
		String imgURL = "";
		try {
			selectImage = con.prepareStatement(SELECT_IMAGE_BY_USER_ID);
			
			selectImage.setInt(1, user_id);
			
			selectImage.execute();
			
			result = selectImage.getResultSet();
			result.next();
			
			int imgId = result.getInt(1);
			
			imgURL = new ImageDAO().getImageURLById(imgId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return imgURL;
	}

}
