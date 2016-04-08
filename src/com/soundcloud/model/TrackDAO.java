package com.soundcloud.model;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mysql.jdbc.Statement;

public class TrackDAO extends AbstractDAO implements ITrackDAO {
	
	private static final String SELECT_PLAYLIST_TRACKS = "SELECT t.track_id FROM tracks t " +
			"JOIN playlists_with_tracks pt ON (pt.track_id = t.track_id) " +
			"JOIN playlists p ON (p.playlist_id = pt.playlist_id) WHERE p.playlist_id = ?;";

	private static TrackDAO trackDAOInstance = null;

	private static final String INSERT_TAG_WITH_TRACK_QUERY = "INSERT INTO tags_with_tracks VALUES (?, ?);";
	private static final String IS_TRACK_LIKED = "SELECT * FROM liked_tracks WHERE track_id = ? AND user_id = ?";
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
		System.out.println("NEW TRACK ID: " + newTrackId);
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

			PreparedStatement getTracks = getCon().prepareStatement(SELECT_ALL_TRACKS);

			ResultSet resultTracks = getTracks.executeQuery();

			while (resultTracks.next()) {
				Track track = new Track();
				int imageid = resultTracks.getInt("img_id");
				int genreId = resultTracks.getInt("genre_id");

				GenreDAO genreDao = GenreDAO.getGenreDAOInstance();
				String genreName = genreDao.getNameById(genreId);

				String img_uri = ImageDAO.getImageDAOInstance().getImageURLById(imageid);
				if (img_uri != null)
					track.setImageURI(ImageDAO.getImageDAOInstance().getImageURLById(imageid));
				
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

				int imageid = resultTracks.getInt("img_id");
				int genreId = resultTracks.getInt("genre_id");

				String img_uri = ImageDAO.getImageDAOInstance().getImageURLById(imageid);

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

				int track_id = results.getInt("track_id");
				track.setUser(UserDAO.getUserDAOInstance().selectUserByTrackId(track_id));

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
			PreparedStatement isTrackLiked = getCon().prepareStatement(IS_TRACK_LIKED);
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return track;
	}

	@Override
	public void likeTrack(int track_id, int user_id) {
		
		try {
			PreparedStatement likeTrack = getCon().prepareStatement("INSERT INTO liked_tracks (track_id , user_id) VALUES (?,?);");
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
			PreparedStatement unlikeTrack = getCon().prepareStatement("DELETE FROM liked_tracks WHERE track_id = ? AND user_id = ?;");
			unlikeTrack.setInt(1, track_id);
			unlikeTrack.setInt(2, user_id);
			
			unlikeTrack.executeUpdate();
	
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
			PreparedStatement ps = getCon().prepareStatement("SELECT * FROM tags WHERE name = ?;");
			ps.setString(1, tag);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				tagId = rs.getInt("tag_id");
			} else {
				// if the tag is not existing, we create it and insert it into tags table
				PreparedStatement insertTagStatement = getCon().prepareStatement("INSERT INTO tags VALUES (null, ?);", Statement.RETURN_GENERATED_KEYS);
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
		System.out.println("TAG ID: " + tagId);
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

}
