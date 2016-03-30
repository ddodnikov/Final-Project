package com.soundcloud.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements ITrackDAO {

	private static final String FIND_TRACKS_QUERY = "SELECT * FROM tracks WHERE title LIKE ?;";
	private static final String INSERT_TRACK = "INSERT INTO tracks (title, genre_id, description, "
			+ "track_uri, likes_count, plays_count, date_added, user_id) VALUES(?,?,?,?,?,?,?,?);";
	private static final String SELECT_ALL_TRACKS = "SELECT * FROM tracks ORDER BY date_added DESC;";
	private static final String SELECT_IMAGE_BY_ID = "SELECT * FROM images WHERE img_id = ?;";
	private static final String INSERT_IMAGE = "INSERT INTO images (img_id, img_uri) VALUES(null, ?);";
	private static final String SELECT_IMAGE_BY_URI = "SELECT * FROM images WHERE img_uri = ?;";
	private static final String UPDATE_TRACK_IMAGE = "UPDATE tracks SET img_id = ? WHERE title = ?;";
	private static final String SELECT_TRACKS_BY_USER_ID = "SELECT * FROM tracks WHERE user_id = ? ORDER BY date_added DESC;";

	private static final Connection con = DBConnection.getDBInstance().getConnection();

	@Override
	public void addTrack(String title, int ganre_id, String description, String uri, int userId) {

		PreparedStatement addTrack = null;

		try {
			addTrack = con.prepareStatement(INSERT_TRACK);

			addTrack.setString(1, title);
			addTrack.setInt(2, ganre_id);
			addTrack.setString(3, description);
			addTrack.setString(4, uri);
			addTrack.setInt(5, 0);
			addTrack.setInt(6, 0);
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

		try {

			PreparedStatement getTracks = con.prepareStatement(SELECT_ALL_TRACKS);
			PreparedStatement getImage = con.prepareStatement(SELECT_IMAGE_BY_ID);

			ResultSet resultTracks = getTracks.executeQuery();

			while (resultTracks.next()) {
				Track track = new Track();
				int imageid = resultTracks.getInt("img_id");
				int genreId = resultTracks.getInt("genre_id");
				getImage.setInt(1, imageid);
				ResultSet resultImages = getImage.executeQuery();
				resultImages.next();

				GenreDAO genreDao = new GenreDAO();
				String genreName = genreDao.getNameById(genreId);

				// track.setImageURI(result2.getString("img_uri"));

				track.setTrackURL(resultTracks.getString("track_uri"));
				track.setDateAdded(resultTracks.getDate("date_added"));
				track.setTitle(resultTracks.getString("title"));
				track.setGanre(genreName);
				track.setDescription(resultTracks.getString("description"));
				track.setNumberOfLikes(resultTracks.getInt("likes_count"));
				track.setNumberOfPlays(resultTracks.getInt("plays_count"));

				tracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tracks;
	}

	@Override
	public void addImage(String uri) {

		PreparedStatement addImage = null;

		try {
			addImage = con.prepareStatement(INSERT_IMAGE);

			addImage.setString(1, uri);

			addImage.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getImageByUri(String uri) {

		PreparedStatement selectImage = null;

		ResultSet result = null;

		int id = 1;
		try {
			selectImage = con.prepareStatement(SELECT_IMAGE_BY_URI);

			selectImage.setString(1, uri);

			selectImage.execute();

			result = selectImage.getResultSet();
			result.next();

			id = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	@Override
	public void updateTrackImage(int imgid, String title) {
		PreparedStatement updateTrackImage = null;

		try {
			updateTrackImage = con.prepareStatement(UPDATE_TRACK_IMAGE);

			updateTrackImage.setInt(1, imgid);
			updateTrackImage.setString(2, title);

			updateTrackImage.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Track> getUserTracks(int userId) {

		List<Track> tracks = new ArrayList<Track>();

		try {

			PreparedStatement getTracks = con.prepareStatement(SELECT_TRACKS_BY_USER_ID);
			PreparedStatement getImage = con.prepareStatement(SELECT_IMAGE_BY_ID);

			getTracks.setInt(1, userId);
			ResultSet resultTracks = getTracks.executeQuery();

			while (resultTracks.next()) {
				Track track = new Track();
				int imageid = resultTracks.getInt("img_id");
				int genreId = resultTracks.getInt("genre_id");
				getImage.setInt(1, imageid);
				ResultSet resultImages = getImage.executeQuery();
				resultImages.next();

				GenreDAO genreDao = new GenreDAO();
				String genreName = genreDao.getNameById(genreId);

				track.setTrackURL(resultTracks.getString("track_uri"));
				track.setDateAdded(resultTracks.getDate("date_added"));
				track.setTitle(resultTracks.getString("title"));
				track.setGanre(genreName);
				track.setDescription(resultTracks.getString("description"));
				track.setNumberOfLikes(resultTracks.getInt("likes_count"));
				track.setNumberOfPlays(resultTracks.getInt("plays_count"));

				tracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tracks;
	}

	@Override
	public List<Track> searchTracksTitleAndGenreByWord(String word) {
		List<Track> foundTracks = new ArrayList<Track>();
		try {
			PreparedStatement foundTracksStatement = con.prepareStatement(FIND_TRACKS_QUERY);
			foundTracksStatement.setString(1, "%" + word + "%");
//			foundTracksStatement.setString(2, "%" + word + "%");
			ResultSet results = foundTracksStatement.executeQuery();
			while (results.next()) {
				Track track = new Track();
				int genreId = results.getInt("genre_id");
				GenreDAO genreDao = new GenreDAO();
				String genreName = genreDao.getNameById(genreId);
				track.setTrackURL(results.getString("track_uri"));
				track.setDateAdded(results.getDate("date_added"));
				track.setTitle(results.getString("title"));
				track.setGanre(genreName);
				track.setDescription(results.getString("description"));
				track.setNumberOfLikes(results.getInt("likes_count"));
				track.setNumberOfPlays(results.getInt("plays_count"));
				foundTracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundTracks;
	}

}
