package com.soundcloud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDAO {
	
	private static final Connection con = DBConnection.getDBInstance().getConnection();
	
	private static final String SELECT_IMAGE_BY_ID = "SELECT img_uri FROM images WHERE img_id = ?;";
	
	public String getImageURLById(int img_id) {

		PreparedStatement selectImage = null;
		
		ResultSet result = null;
		
		String url = "";
		try {
			selectImage = con.prepareStatement(SELECT_IMAGE_BY_ID);
			
			selectImage.setInt(1, img_id);
			
			selectImage.execute();
			
			result = selectImage.getResultSet();
			result.next();
			
			url = result.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return url;
	}

}
