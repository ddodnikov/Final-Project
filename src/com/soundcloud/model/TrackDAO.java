package com.soundcloud.model;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;

public class TrackDAO implements ITrackDAO {

	private static final String IS_TRACK_LIKED = "SELECT * FROM liked_tracks WHERE track_id = ? AND user_id = ?";
	private static final String FIND_TRACKS_QUERY = "SELECT * FROM tracks WHERE title LIKE ? OR "
			+ "genre_id in (SELECT genre_id FROM genres WHERE name LIKE ?) OR "
			+ "track_id in (SELECT track_id FROM tags_with_tracks WHERE tag_id IN "
			+ "(SELECT tag_id FROM tags WHERE name LIKE ?)) LIMIT 10 OFFSET ?;";
	private static final String INSERT_TRACK = "INSERT INTO tracks (title, genre_id, description, "
			+ "track_uri, likes_count, plays_count, date_added, user_id) VALUES(?,?,?,?,?,?,?,?);";
	private static final String SELECT_ALL_TRACKS = "SELECT * FROM tracks ORDER BY date_added DESC;";
	private static final String UPDATE_TRACK_IMAGE = "UPDATE tracks SET img_id = ? WHERE title = ?;";
	private static final String SELECT_TRACKS_BY_USER_ID = "SELECT * FROM tracks WHERE user_id = ? "
			+ "ORDER BY date_added DESC LIMIT 10 OFFSET ?;";
	private static final String SELECT_LATEST_TRACKS = "SELECT * FROM tracks order by date_added desc limit 50;";
	private static final String SELECT_MOST_PLAYED_TRACKS = "SELECT * FROM tracks order by plays_count desc limit 50;";
	private static final String SELECT_MOST_LIKED_TRACKS = "SELECT * FROM tracks order by likes_count desc limit 50;";

	public static final int NUMBER_OF_TRACKS_PER_PAGE = 10;

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
			addTrack.setObject(7, new Timestamp(new Date().getTime()));
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

			ResultSet resultTracks = getTracks.executeQuery();

			while (resultTracks.next()) {
				Track track = new Track();
				int imageid = resultTracks.getInt("img_id");
				int genreId = resultTracks.getInt("genre_id");

				GenreDAO genreDao = new GenreDAO();
				String genreName = genreDao.getNameById(genreId);

				String img_uri = new ImageDAO().getImageURLById(imageid);
				if (img_uri != null)
					track.setImageURI(new ImageDAO().getImageURLById(imageid));

				int track_id = resultTracks.getInt("track_id");
				track.setUser(new UserDAO().selectUserByTrackId(track_id));

				track.setId(track_id);
				track.setTrackURL(resultTracks.getString("track_uri"));
				track.setDateAdded(resultTracks.getTimestamp("date_added"));
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
	public List<Track> getUserTracks(int userId, int offset) {

		List<Track> tracks = new ArrayList<Track>();

		try {

			PreparedStatement getTracks = con.prepareStatement(SELECT_TRACKS_BY_USER_ID);
			getTracks.setInt(1, userId);
			getTracks.setInt(2, offset);
			ResultSet resultTracks = getTracks.executeQuery();

			while (resultTracks.next()) {

				Track track = new Track();

				int imageid = resultTracks.getInt("img_id");
				int genreId = resultTracks.getInt("genre_id");

				String img_uri = new ImageDAO().getImageURLById(imageid);

				String genreName = new GenreDAO().getNameById(genreId);

				int track_id = resultTracks.getInt("track_id");
				track.setUser(new UserDAO().selectUserByTrackId(track_id));

				track.setId(track_id);
				track.setTrackURL(img_uri);
				track.setDateAdded(resultTracks.getTimestamp("date_added"));
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
	public List<Track> searchTracksTitleTagsAndGenreByWord(String word, int offset) {
		List<Track> foundTracks = new ArrayList<Track>();
		try {
			PreparedStatement foundTracksStatement = con.prepareStatement(FIND_TRACKS_QUERY);
			foundTracksStatement.setString(1, "%" + word + "%");
			foundTracksStatement.setString(2, "%" + word + "%");
			foundTracksStatement.setString(3, "%" + word + "%");
			foundTracksStatement.setInt(4, offset);
			ResultSet results = foundTracksStatement.executeQuery();
			while (results.next()) {
				Track track = new Track();
				int genreId = results.getInt("genre_id");
				GenreDAO genreDao = new GenreDAO();
				String genreName = genreDao.getNameById(genreId);
				track.setId(results.getInt("track_id"));
				track.setTrackURL(results.getString("track_uri"));
				track.setDateAdded(results.getTimestamp("date_added"));
				track.setTitle(results.getString("title"));
				track.setGanre(genreName);
				track.setDescription(results.getString("description"));
				track.setNumberOfLikes(results.getInt("likes_count"));
				track.setNumberOfPlays(results.getInt("plays_count"));

				int track_id = results.getInt("track_id");
				track.setUser(new UserDAO().selectUserByTrackId(track_id));

				foundTracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundTracks;
	}

	public List<Track> getMostPlayedTracks() {
		return getTrackList(SELECT_MOST_PLAYED_TRACKS);
	}

	public List<Track> getLatestTracks() {
		return getTrackList(SELECT_LATEST_TRACKS);
	}

	public List<Track> getMostLikedTracks() {
		return getTrackList(SELECT_MOST_LIKED_TRACKS);
	}

	private List<Track> getTrackList(String sql) {
		List<Track> foundTracks = new ArrayList<Track>();
		try {
			PreparedStatement foundTracksStatement = con.prepareStatement(sql);
			ResultSet results = foundTracksStatement.executeQuery();
			while (results.next()) {
				Track track = new Track();
				int genreId = results.getInt("genre_id");
				GenreDAO genreDao = new GenreDAO();
				String genreName = genreDao.getNameById(genreId);
				track.setTrackURL(results.getString("track_uri"));
				track.setDateAdded(results.getTimestamp("date_added"));
				track.setTitle(results.getString("title"));
				track.setGanre(genreName);
				track.setDescription(results.getString("description"));
				track.setNumberOfLikes(results.getInt("likes_count"));
				track.setNumberOfPlays(results.getInt("plays_count"));

				int track_id = results.getInt("track_id");
				track.setUser(new UserDAO().selectUserByTrackId(track_id));

				foundTracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundTracks;
	}

	@Override
	public boolean isTrackLikedByUser(int track_id, int user_id) {

		try {
			PreparedStatement isTrackLiked = con.prepareStatement(IS_TRACK_LIKED);
			isTrackLiked.setInt(1, track_id);
			isTrackLiked.setInt(2, user_id);

			ResultSet results = isTrackLiked.executeQuery();

			if (results.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void likeTrack(int track_id, int user_id) {
		
		try {
			PreparedStatement likeTrack = con.prepareStatement("INSERT INTO liked_tracks (track_id , user_id) VALUES (?,?);");
			likeTrack.setInt(1, track_id);
			likeTrack.setInt(2, user_id);
			
			likeTrack.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void unlikeTrack(int track_id, int user_id) {
		
		try {
			PreparedStatement unlikeTrack = con.prepareStatement("DELETE FROM liked_tracks WHERE track_id = ? AND user_id = ?;");
			unlikeTrack.setInt(1, track_id);
			unlikeTrack.setInt(2, user_id);
			
			unlikeTrack.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
