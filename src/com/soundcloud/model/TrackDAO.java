package com.soundcloud.model;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.mysql.jdbc.Statement;
import com.soundcloud.exceptions.TrackAlreadyLikedException;

public class TrackDAO extends AbstractDAO implements ITrackDAO {
	
	private static final String GET_TRACK_LIKES_COUNT_QUERY = "SELECT likes_count FROM tracks WHERE track_id = ?;";

	private static final String INSERT_TAG_QUERY = "INSERT INTO tags VALUES (null, ?);";

	private static final String CHECK_IF_TAG_IS_EXISTING_QUERY = "SELECT * FROM tags WHERE name = ?;";

	private static final String INSERT_TRACK_INTO_UNLIKED_QUERY = "INSERT INTO unliked_tracks VALUES (?,?);";

	private static final String DELETE_TRACK_FROM_LIKED_QUERY = "DELETE FROM liked_tracks WHERE track_id = ? AND user_id = ?;";

	private static final String INSERT_TRACK_INTO_LIKED_QUERY = "INSERT INTO liked_tracks VALUES (?, ?);";

	private static final String DELETE_TRACK_FROM_UNLIKED_QUERY = "DELETE FROM unliked_tracks WHERE track_id = ? AND user_id = ?;";

	private static final String CHECK_IF_TRACK_HAS_BEEN_UNLIKED_BY_USER_QUERY = "SELECT * FROM unliked_tracks WHERE track_id = ? AND user_id = ?;";

	private static final String CHECK_IF_TRACK_IS_LIKED_BY_USER_QUERY = "SELECT * FROM liked_tracks WHERE track_id = ? AND user_id = ?;";

	private static final String INCREMENT_TRACK_LIKES_COUNT = "UPDATE tracks SET likes_count = likes_count + ? WHERE track_id = ?;";
	
	private static final String DECREMENT_TRACK_LIKES_COUNT = "UPDATE tracks SET likes_count = likes_count - ? WHERE track_id = ?;";

	private static final String INCREMENT_TRACK_PLAYS_COUNT_BY_1 = "UPDATE tracks SET plays_count = plays_count + 1 WHERE track_id = ?;";

	private static final String SELECT_PLAYLIST_TRACKS = "SELECT t.track_id FROM tracks t " +
			"JOIN playlists_with_tracks pt ON (pt.track_id = t.track_id) " +
			"JOIN playlists p ON (p.playlist_id = pt.playlist_id) WHERE p.playlist_id = ?;";

	private static TrackDAO trackDAOInstance = null;

	private static final String INSERT_TAG_WITH_TRACK_QUERY = "INSERT INTO tags_with_tracks VALUES (?, ?);";
	private static final String GET_TRACK_IMAGE_URI = "SELECT * FROM tracks WHERE track_id = ?;";
	
	private static final String INSERT_TRACK = "INSERT INTO tracks (title, genre_id, description, "
			+ "track_uri, likes_count, plays_count, date_added, user_id) VALUES(?,?,?,?,?,?,?,?);";
	private static final String SELECT_ALL_TRACKS = "SELECT * FROM tracks ORDER BY date_added DESC;";
	private static final String UPDATE_TRACK_IMAGE = "UPDATE tracks SET img_id = ? WHERE title = ?;";
	private static final String SELECT_TRACKS_BY_USER_ID = "SELECT * FROM tracks WHERE user_id = ? "
			+ "ORDER BY date_added DESC LIMIT 5 OFFSET ?;";
	private static final String SELECT_LIKED_TRACKS_BY_USER_ID = 
			"SELECT track_id FROM liked_tracks WHERE user_id = ? LIMIT 5 OFFSET ?;";
	private static final String SELECT_LATEST_TRACKS = "SELECT * FROM tracks order by date_added desc limit 5;";
	private static final String SELECT_MOST_PLAYED_TRACKS = "SELECT * FROM tracks order by plays_count desc limit 5;";
	private static final String SELECT_MOST_LIKED_TRACKS = "SELECT * FROM tracks order by likes_count desc limit 5;";
	
	private static final String SELECT_TRACK_TAGS = "SELECT t.name FROM tags t JOIN tags_with_tracks tt" + 
							" ON t.tag_id = tt.tag_id WHERE track_id = ?;";

	public static final int NUMBER_OF_TRACKS_PER_PAGE = 5;
	
	private TrackDAO() {

	}

	public static TrackDAO getTrackDAOInstance() {
		synchronized (TrackDAO.class) {
			if (TrackDAO.trackDAOInstance == null) {
				TrackDAO.trackDAOInstance = new TrackDAO();
			}
		}
		return TrackDAO.trackDAOInstance;
	}

	@Override
	public void addTrack(String title, int ganre_id, String description, String uri, int userId, Set<String> tags) {

		PreparedStatement addTrack = null;
		int newTrackId = 0;
		
		try {
			addTrack = getCon().prepareStatement(INSERT_TRACK, Statement.RETURN_GENERATED_KEYS);
			addTrack.setString(1, title);
			addTrack.setInt(2, ganre_id);
			addTrack.setString(3, description);
			addTrack.setString(4, uri);
			addTrack.setInt(5, 0);
			addTrack.setInt(6, 0);
			addTrack.setObject(7, new Timestamp(new Date().getTime()));
			addTrack.setInt(8, userId);
			addTrack.executeUpdate();
			ResultSet newTrackResult = addTrack.getGeneratedKeys();
			newTrackResult.next();
			newTrackId = newTrackResult.getInt(1); //column track_id
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (String tag : tags) {
			int tagId = addTag(tag);
			insertTagWithTrack(tagId, newTrackId);
		}
	}

	private void insertTagWithTrack(int newTrackId, int tagId) {
		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_TAG_WITH_TRACK_QUERY);
			ps.setInt(1, newTrackId);
			ps.setInt(2, tagId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public List<Track> getAllTracks() {

		List<Track> tracks = new ArrayList<Track>();

		try {

			PreparedStatement getTracks = getCon().prepareStatement(SELECT_ALL_TRACKS);

			ResultSet resultTracks = getTracks.executeQuery();

			while (resultTracks.next()) {
				Track track = new Track();

				int genreId = resultTracks.getInt("genre_id");

				GenreDAO genreDao = GenreDAO.getGenreDAOInstance();
				String genreName = genreDao.getNameById(genreId);
				
				int track_id = resultTracks.getInt("track_id");
				track.setUser(UserDAO.getUserDAOInstance().selectUserByTrackId(track_id));
				
				track.setImageID(resultTracks.getInt("img_id"));
				track.setId(track_id);
				track.setTrackURL(resultTracks.getString("track_uri"));
				track.setDateAdded(resultTracks.getTimestamp("date_added"));
				track.setTitle(resultTracks.getString("title"));
				track.setGanre(genreName);
				track.setDescription(resultTracks.getString("description"));
				track.setNumberOfLikes(resultTracks.getInt("likes_count"));
				track.setNumberOfPlays(resultTracks.getInt("plays_count"));
				track.setTags(getTrackTags(track_id));

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
			updateTrackImage = getCon().prepareStatement(UPDATE_TRACK_IMAGE);

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

			PreparedStatement getTracks = getCon().prepareStatement(SELECT_TRACKS_BY_USER_ID);
			getTracks.setInt(1, userId);
			getTracks.setInt(2, offset);
			ResultSet resultTracks = getTracks.executeQuery();

			while (resultTracks.next()) {

				Track track = new Track();

				int genreId = resultTracks.getInt("genre_id");

				String genreName = GenreDAO.getGenreDAOInstance().getNameById(genreId);

				int track_id = resultTracks.getInt("track_id");
				track.setUser(UserDAO.getUserDAOInstance().selectUserByTrackId(track_id));

				track.setId(track_id);
				track.setTrackURL(resultTracks.getString("track_uri"));
				track.setDateAdded(resultTracks.getTimestamp("date_added"));
				track.setTitle(resultTracks.getString("title"));
				track.setGanre(genreName);
				track.setDescription(resultTracks.getString("description"));
				track.setNumberOfLikes(resultTracks.getInt("likes_count"));
				track.setNumberOfPlays(resultTracks.getInt("plays_count"));
				track.setImageID(resultTracks.getInt("img_id"));
				
				track.setTags(getTrackTags(track_id));

				tracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tracks;
	}

	@Override
	public List<Track> searchTracksTitleTagsAndGenreByWord(Set<String> words, int offset ) {
		List<Track> foundTracks = new ArrayList<Track>();
		
		StringBuilder title = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		StringBuilder sql = new StringBuilder();
		
		for(String word : words) {
			title.append("'%" + word + "%' OR title LIKE ");
			name.append("'%" + word + "%' OR name LIKE ");
		}
		
		title.delete(title.length() - 15, title.length());
		name.delete(name.length() - 14, name.length());
		
		sql.append("SELECT * FROM tracks WHERE title LIKE " + title
				+ " OR genre_id in (SELECT genre_id FROM genres WHERE name LIKE " + name
				+ ") OR track_id in (SELECT track_id FROM tags_with_tracks WHERE tag_id IN "
				+ "(SELECT tag_id FROM tags WHERE name LIKE " + name + ")) LIMIT 5 OFFSET ?;");
		
		try {
			PreparedStatement foundTracksStatement = getCon().prepareStatement(sql.toString());
			foundTracksStatement.setInt(1, offset);
			
			ResultSet results = foundTracksStatement.executeQuery();
			while (results.next()) {
				Track track = new Track();
				int trackId = results.getInt("track_id");
				int genreId = results.getInt("genre_id");
				GenreDAO genreDao = GenreDAO.getGenreDAOInstance();
				String genreName = genreDao.getNameById(genreId);
				track.setId(trackId);
				track.setTrackURL(results.getString("track_uri"));
				track.setDateAdded(results.getTimestamp("date_added"));
				track.setTitle(results.getString("title"));
				track.setGanre(genreName);
				track.setDescription(results.getString("description"));
				track.setNumberOfLikes(results.getInt("likes_count"));
				track.setNumberOfPlays(results.getInt("plays_count"));
				track.setImageID(results.getInt("img_id"));
				track.setTags(getTrackTags(trackId));

				track.setUser(UserDAO.getUserDAOInstance().selectUserByTrackId(trackId));

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
			PreparedStatement foundTracksStatement = getCon().prepareStatement(sql);
			ResultSet results = foundTracksStatement.executeQuery();
			while (results.next()) {
				Track track = new Track();
				int genreId = results.getInt("genre_id");
				GenreDAO genreDao = GenreDAO.getGenreDAOInstance();
				String genreName = genreDao.getNameById(genreId);
				track.setId(results.getInt("track_id"));
				track.setTrackURL(results.getString("track_uri"));
				track.setDateAdded(results.getTimestamp("date_added"));
				track.setTitle(results.getString("title"));
				track.setGanre(genreName);
				track.setDescription(results.getString("description"));
				track.setNumberOfLikes(results.getInt("likes_count"));
				track.setNumberOfPlays(results.getInt("plays_count"));
				track.setImageID(results.getInt("img_id"));
				track.setTags(getTrackTags(track.getId()));

				track.setUser(UserDAO.getUserDAOInstance().selectUserByTrackId(track.getId()));

				foundTracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundTracks;
	}

	@Override
	public boolean isTrackLikedByUser(int trackId, int userId) {
		try {
			PreparedStatement isTrackLiked = getCon().prepareStatement(CHECK_IF_TRACK_IS_LIKED_BY_USER_QUERY);
			isTrackLiked.setInt(1, trackId);
			isTrackLiked.setInt(2, userId);
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
	public boolean isTrackUnlikedByUser(int trackId, int userId) {
		try {
			PreparedStatement isTrackUnlikedPS = getCon().prepareStatement(CHECK_IF_TRACK_HAS_BEEN_UNLIKED_BY_USER_QUERY);
			isTrackUnlikedPS.setInt(1, trackId);
			isTrackUnlikedPS.setInt(2, userId);
			ResultSet isTrackUnlikedResult = isTrackUnlikedPS.executeQuery();
			if (isTrackUnlikedResult.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

@Override
	public Track getTrackById(int trackId) {
		Track track = null;
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_TRACK_IMAGE_URI);
			ps.setInt(1, trackId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
					int genreId = rs.getInt("genre_id");

					GenreDAO genreDao = GenreDAO.getGenreDAOInstance();

					String genreName = genreDao.getNameById(genreId);	
					
					track = new Track();
					
					track.setUser(UserDAO.getUserDAOInstance().selectUserByTrackId(trackId));
					track.setId(trackId);
					track.setGanre(genreName);
					track.setTitle(rs.getString("title"));
					track.setDescription(rs.getString("description"));
					track.setTrackURL(rs.getString("track_uri"));
					track.setNumberOfLikes(rs.getInt("likes_count"));
					track.setNumberOfPlays(rs.getInt("plays_count"));
					track.setDateAdded(rs.getTimestamp("date_added"));
					track.setImageID(rs.getInt("img_id"));
					track.setTags(getTrackTags(trackId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return track;
	}

	@Override
	public void incrementTrackLikesCount(int trackId, int userId) {
		int incrementBy = 0;
		try {
			if (isTrackUnlikedByUser(trackId, userId)) {
				incrementBy = 2;
			} else {
				incrementBy = 1;
			}
			PreparedStatement ps = getCon().prepareStatement(INCREMENT_TRACK_LIKES_COUNT);
			ps.setInt(1, incrementBy);
			ps.setInt(2, trackId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void decrementTrackLikesCount(int trackId, int userId) {
		int decrementBy = 0;
		try {
			if (isTrackLikedByUser(trackId, userId)) {
				decrementBy = 2;
			} else {
				decrementBy = 1;
			}
			PreparedStatement ps = getCon().prepareStatement(DECREMENT_TRACK_LIKES_COUNT);
			ps.setInt(1, decrementBy);
			ps.setInt(2, trackId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void likeTrack(int trackId, int userId) throws TrackAlreadyLikedException {
		try {
			// check if the track is already liked by user
			PreparedStatement checkIfTrackIsLikedPS = getCon().prepareStatement(CHECK_IF_TRACK_IS_LIKED_BY_USER_QUERY);
			checkIfTrackIsLikedPS.setInt(1, trackId);
			checkIfTrackIsLikedPS.setInt(2, userId);
			ResultSet isTrackLikedResult = checkIfTrackIsLikedPS.executeQuery();
			if (isTrackLikedResult.next()) {
				// user has already liked this track
				throw new TrackAlreadyLikedException("You have already liked this track!");
			} else {
				// check if user has previously unliked this track
				PreparedStatement checkIfTrackHasBeenUnlikedPS = getCon().prepareStatement(CHECK_IF_TRACK_HAS_BEEN_UNLIKED_BY_USER_QUERY);
				checkIfTrackHasBeenUnlikedPS.setInt(1, trackId);
				checkIfTrackHasBeenUnlikedPS.setInt(2, userId);
				ResultSet hasTrackBeenUnlikedResult = checkIfTrackHasBeenUnlikedPS.executeQuery();
				if (hasTrackBeenUnlikedResult.next()) {
					// user has previously unliked this track
					// delete track from unliked
					PreparedStatement deleteTrackFromUnlikedPS = getCon().prepareStatement(DELETE_TRACK_FROM_UNLIKED_QUERY);
					deleteTrackFromUnlikedPS.setInt(1, trackId);
					deleteTrackFromUnlikedPS.setInt(2, userId);
					deleteTrackFromUnlikedPS.executeUpdate();
				}
				// insert the track into liked_tracks table
				PreparedStatement insertTrackIntoLikedPS = getCon().prepareStatement(INSERT_TRACK_INTO_LIKED_QUERY);
				insertTrackIntoLikedPS.setInt(1, trackId);
				insertTrackIntoLikedPS.setInt(2, userId);
				insertTrackIntoLikedPS.executeUpdate();
				// increment track likes in tracks table
				incrementTrackLikesCount(trackId, userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void unlikeTrack(int trackId, int userId) throws TrackAlreadyLikedException {
		// check if user has already unliked the track
		try {
			PreparedStatement checkIfTrackIsUnliked = getCon().prepareStatement(CHECK_IF_TRACK_HAS_BEEN_UNLIKED_BY_USER_QUERY);
			checkIfTrackIsUnliked.setInt(1, trackId);
			checkIfTrackIsUnliked.setInt(2, userId);
			ResultSet isTrackUnlikedResult = checkIfTrackIsUnliked.executeQuery();
			if (isTrackUnlikedResult.next()) {
				// user has already unliked this track
				throw new TrackAlreadyLikedException("You have already unliked this track!");
			} else {
				// check if user has previously liked this track
				PreparedStatement checkIfTrackHasBeenLikedPS = getCon().prepareStatement(CHECK_IF_TRACK_IS_LIKED_BY_USER_QUERY);
				checkIfTrackHasBeenLikedPS.setInt(1, trackId);
				checkIfTrackHasBeenLikedPS.setInt(2, userId);
				ResultSet hasTrackBeenLikedResult = checkIfTrackHasBeenLikedPS.executeQuery();
				if (hasTrackBeenLikedResult.next()) {
					// user has previously liked this tracks
					// delete track from liked
					PreparedStatement deleteTrackFromLikedPS = getCon().prepareStatement(DELETE_TRACK_FROM_LIKED_QUERY);
					deleteTrackFromLikedPS.setInt(1, trackId);
					deleteTrackFromLikedPS.setInt(2, userId);
					deleteTrackFromLikedPS.executeUpdate();
				}
				// insert the track into unliked_tracks table
				PreparedStatement insertTrackIntoUnlikedPS = getCon().prepareStatement(INSERT_TRACK_INTO_UNLIKED_QUERY);
				insertTrackIntoUnlikedPS.setInt(1, trackId);
				insertTrackIntoUnlikedPS.setInt(2, userId);
				insertTrackIntoUnlikedPS.executeUpdate();
				// decrement track likes in tracks table
				decrementTrackLikesCount(trackId, userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTrackImageUri(int trackId) {
		int imgId = 0;
		String trackImageUri = "";
		ImageDAO imageDao = ImageDAO.getImageDAOInstance();
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_TRACK_IMAGE_URI);
			ps.setInt(1, trackId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				imgId = rs.getInt("img_id");
				trackImageUri = imageDao.getImageURLById(imgId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trackImageUri;
	}

	@Override
	public int addTag(String tag) {
		int tagId = 0;
		try {
			// first we have to check if the tag is existing in the tags table
			PreparedStatement ps = getCon().prepareStatement(CHECK_IF_TAG_IS_EXISTING_QUERY);
			ps.setString(1, tag);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				tagId = rs.getInt("tag_id");
			} else {
				// if the tag is not existing, we create it and insert it into tags table
				PreparedStatement insertTagStatement = getCon().prepareStatement(INSERT_TAG_QUERY, Statement.RETURN_GENERATED_KEYS);
				insertTagStatement.setString(1, tag);
				insertTagStatement.executeUpdate();
				// get the ID of the inserted tag
				ResultSet newTagResult = insertTagStatement.getGeneratedKeys();
				newTagResult.next();
				tagId = newTagResult.getInt(1); // column tag_id
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tagId;
	}
	
	public List<Track> getUserLikedTracks(int user_id, int offset) {
		
		List<Track> likedTracks = new ArrayList<Track>();

		try {

			PreparedStatement getLikedTracks = getCon().prepareStatement(SELECT_LIKED_TRACKS_BY_USER_ID);
			getLikedTracks.setInt(1, user_id);
			getLikedTracks.setInt(2, offset);
			ResultSet resultId = getLikedTracks.executeQuery();

			while (resultId.next()) {

				Track track = getTrackById(resultId.getInt(1));

				likedTracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return likedTracks;
	}
	
	public List<Track> getPlaylistTracks(int playlistId) {
		
		List<Track> playlistTracks = new ArrayList<Track>();

		try {

			PreparedStatement getPlaylistTracks = getCon().prepareStatement(SELECT_PLAYLIST_TRACKS);
			getPlaylistTracks.setInt(1, playlistId);

			ResultSet resultId = getPlaylistTracks.executeQuery();

			while (resultId.next()) {

				Track track = getTrackById(resultId.getInt(1));

				playlistTracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playlistTracks;
	}
	
	@Override
	public void addPlayToTrack(int trackId) {
		try {
			PreparedStatement ps = getCon().prepareStatement(INCREMENT_TRACK_PLAYS_COUNT_BY_1);
			ps.setInt(1, trackId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public int getTrackLikes(int trackId) {
		int trackLikes = 0;
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_TRACK_LIKES_COUNT_QUERY);
			ps.setInt(1, trackId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				trackLikes = rs.getInt("likes_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trackLikes;
	}
	
	private List<String> getTrackTags(int trackId) {
		
		List<String> tags = new ArrayList<String>();
		
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_TRACK_TAGS);
			ps.setInt(1, trackId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tags.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
		
	}

}
