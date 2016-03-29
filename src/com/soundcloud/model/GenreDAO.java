package com.soundcloud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDAO implements IGenreDAO{
	private static final String GET_GENRE_NAME = "SELECT name FROM genres WHERE genre_id = ?;";
	private static final String GET_GENRE_ID = "SELECT genre_id FROM genres WHERE name = ?;";

	@Override
	public String getNameById(int genreId) {
		Connection con = DBConnection.getDBInstance().getConnection();
		String genreName = "";
		try {
			PreparedStatement ps = con.prepareStatement(GET_GENRE_NAME);
			ps.setInt(1, genreId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				genreName = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genreName;
	}
	
	@Override
	public int getIdByName(String name) {
		Connection con = DBConnection.getDBInstance().getConnection();
		int genreId = 0;
		try {
			PreparedStatement ps = con.prepareStatement(GET_GENRE_ID);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				genreId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genreId;
	}
}
