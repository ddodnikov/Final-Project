package com.soundcloud.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class TrackDAO implements ITrackDAO{

	private static final String INSERT_TRACK = "INSERT INTO tracks (title, genre, description, "
			+ "track_uri, likes_count, plays_count, date_added, user_id) VALUES(?,?,?,?,?,?,?,?);";
	
	@Override
	public void addTrack(String title, String ganre, String description, String uri, int userId) {
		
		Connection con = DBConnection.getDBInstance().getConnection();

		PreparedStatement addTrack = null;
		
		try {
			addTrack = con.prepareStatement(INSERT_TRACK);
			
			addTrack.setString(1, title);
			addTrack.setString(2, ganre);
			addTrack.setString(3, description);
			addTrack.setString(4, uri);
			addTrack.setInt(5, 23);
			addTrack.setInt(6, 45);
			addTrack.setDate(7, Date.valueOf(LocalDate.now()));
			addTrack.setInt(8, userId);
			
			addTrack.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteTrack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTrack() {
		// TODO Auto-generated method stub
		
	}

}
