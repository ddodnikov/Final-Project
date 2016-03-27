package com.soundcloud.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements ITrackDAO{

	private static final String INSERT_TRACK = "INSERT INTO tracks (title, genre, description, "
			+ "track_uri, likes_count, plays_count, date_added, user_id) VALUES(?,?,?,?,?,?,?,?);";
	private static final String SELECT_ALL_TRACKS = "SELECT * FROM tracks;";
	private static final String SELECT_IMAGE_BY_ID = "SELECT * FROM images WHERE img_id = ?;";
	
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

	@Override
	public List<Track> getAllTracks() {
		
		List<Track> tracks = new ArrayList<Track>();
		
		Connection con = DBConnection.getDBInstance().getConnection();
		
		try {
			
			PreparedStatement getTracks = con.prepareStatement(SELECT_ALL_TRACKS);
			PreparedStatement getImage  = con.prepareStatement(SELECT_IMAGE_BY_ID);
			
			ResultSet result =  getTracks.executeQuery();
			
			while(result.next()) {
				Track track = new Track();
				
				int imageid = result.getInt("img_id");
				getImage.setInt(1, imageid);
				ResultSet result2 =  getImage.executeQuery();
				result2.next();
				
				track.setImageURI(result2.getString("img_url"));
				
				track.setTrackURL(result.getString("track_url"));
				track.setDateAdded(result.getDate("date_added"));
				track.setTitle(result.getString("title"));
				track.setGanre(result.getString("ganre"));
				track.setDescription(result.getString("description"));
				track.setNumberOfLikes(result.getInt("likes_count"));
				track.setNumberOfPlays(result.getInt("plays_count"));
				
				
				tracks.add(track);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tracks;
		
	}

}
