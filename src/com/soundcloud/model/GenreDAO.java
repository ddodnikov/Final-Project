package com.soundcloud.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends AbstractDAO implements IGenreDAO {
	
	private static GenreDAO genreDAOInstance = null;
	
	private static final String GET_GENRE_NAME = "SELECT name FROM genres WHERE genre_id = ?;";
	private static final String GET_GENRE_ID = "SELECT genre_id FROM genres WHERE name = ?;";
	
	private GenreDAO() {

	}

	public static GenreDAO getGenreDAOInstance() {
		synchronized (TrackDAO.class) {
			if (GenreDAO.genreDAOInstance == null) {
				GenreDAO.genreDAOInstance = new GenreDAO();
			}
		}
		return GenreDAO.genreDAOInstance;
	}
	
	@Override
	public String getNameById(int genreId) {
		
		String genreName = "";
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_GENRE_NAME);
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
		int genreId = 0;
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_GENRE_ID);
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
	
	@Override
	public List<String> getAllGenreNames() {
		List<String> allGenres = new ArrayList<String>();
		try {
			PreparedStatement ps = getCon().prepareStatement("SELECT * FROM genres;");
			ResultSet genresResult = ps.executeQuery();
			while (genresResult.next()) {
				String genreName = genresResult.getString(2);
				allGenres.add(genreName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allGenres;
	}
}
