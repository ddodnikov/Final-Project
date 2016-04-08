package com.soundcloud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDAO extends AbstractDAO {
	
	private static final String SELECT_IMAGE_BY_ID = "SELECT img_uri FROM images WHERE img_id = ?;";
	public static final String INSERT_IMAGE = "INSERT INTO images (img_id, img_uri) VALUES(null, ?);";
	private static final String SELECT_IMAGE_BY_URI = "SELECT img_id FROM images WHERE img_uri = ?;";
	
	public String getImageURLById(int img_id) {

		PreparedStatement selectImage = null;
		
		ResultSet result = null;
		
		String url = "";
		try {
			selectImage = getCon().prepareStatement(SELECT_IMAGE_BY_ID);
			
			selectImage.setInt(1, img_id);
			
			selectImage.execute();
			
			result = selectImage.getResultSet();
			
			if(result.next())
				url = result.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return url;
	}
	
	public int getImagIdByUri(String img_uri) {

		PreparedStatement selectImage = null;
		
		ResultSet result = null;
		
		int id = 0;
		try {
			selectImage = getCon().prepareStatement(SELECT_IMAGE_BY_URI);
			
			selectImage.setString(1, img_uri);
			
			selectImage.execute();
			
			result = selectImage.getResultSet();
			result.next();
			
			id = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public void addImage(String uri) {

		PreparedStatement addImage = null;

		try {
			addImage = getCon().prepareStatement(INSERT_IMAGE);

			addImage.setString(1, uri);

			addImage.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			// throw exception
		}
	}
	
	

}
