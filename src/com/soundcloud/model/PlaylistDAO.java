package com.soundcloud.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO extends AbstractDAO implements IPlaylistDAO {

	private static final String INSERT_INTO_PLAYLISTS_WITH_TRACKS = "INSERT INTO playlists_with_tracks (playlist_id, track_id) VALUES (?,?);";

	private static final String SELECT_PLAYLIST_BY_ID = "SELECT * FROM playlists WHERE playlist_id = ?";

	private static PlaylistDAO playlistDAOInstance = null;

	private static final String INSERT_PLAYLIST = "INSERT INTO playlists (title, tracks_count, img_id, user_id)"
			+ " VALUES(?,?,?,?);";

	private static final String SELECT_PLAYLISTS_BY_USER_ID = "SELECT * FROM playlists WHERE user_id = ? LIMIT 5 OFFSET ?;";

	private static final String GET_PLAYLIST_IMAGE_URI = "SELECT * FROM playlists WHERE playlist_id = ?;";

	private PlaylistDAO() {

	}

	public static PlaylistDAO getPlaylistDAOInstance() {
		synchronized (TrackDAO.class) {
			if (PlaylistDAO.playlistDAOInstance == null) {
				PlaylistDAO.playlistDAOInstance = new PlaylistDAO();
			}
		}
		return PlaylistDAO.playlistDAOInstance;
	}

	@Override
	public void addPlaylist(int img_id, String title, int userId) {

		PreparedStatement addPlaylist = null;

		try {
			addPlaylist = getCon().prepareStatement(INSERT_PLAYLIST);

			addPlaylist.setString(1, title);
			addPlaylist.setInt(2, 0);
			addPlaylist.setInt(3, img_id);
			addPlaylist.setInt(4, userId);

			addPlaylist.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePlaylist() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Playlist> getUserPlaylists(int userId, int offset) {

		List<Playlist> playlists = new ArrayList<Playlist>();

		try {

			PreparedStatement getPlaylists = getCon().prepareStatement(SELECT_PLAYLISTS_BY_USER_ID);
			getPlaylists.setInt(1, userId);
			getPlaylists.setInt(2, offset);
			ResultSet resultTracks = getPlaylists.executeQuery();

			while (resultTracks.next()) {

				Playlist playlist = new Playlist();

				int imageid = resultTracks.getInt("img_id");

				User user = UserDAO.getUserDAOInstance().getUserById(resultTracks.getInt("user_id"));

				String img_uri = ImageDAO.getImageDAOInstance().getImageURLById(imageid);

				playlist.setTitle(resultTracks.getString("title"));
				playlist.setImageURI(img_uri);
				playlist.setNumberOfTracks(resultTracks.getInt("tracks_count"));
				playlist.setUser(user);
				playlist.setId(resultTracks.getInt("playlist_id"));

				playlists.add(playlist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playlists;
	}

	public String getPlaylistImageUri(int playlistId) {
		int imgId = 0;
		String playlistImageUri = "";
		ImageDAO imageDao = ImageDAO.getImageDAOInstance();
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_PLAYLIST_IMAGE_URI);
			ps.setInt(1, playlistId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				imgId = rs.getInt("img_id");
				playlistImageUri = imageDao.getImageURLById(imgId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playlistImageUri;

	}
	
	public Playlist getPlaylistById(int playlistId) {
		
		Playlist playlist = new Playlist();
		
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_PLAYLIST_BY_ID);
			ps.setInt(1, playlistId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				playlist.setId(rs.getInt("playlist_id"));
				playlist.setTitle(rs.getString("title"));
				playlist.setNumberOfTracks(rs.getInt("tracks_count"));
				
				playlist.setUser(UserDAO.getUserDAOInstance().getUserById(rs.getInt("user_id")));
				playlist.setImageURI(ImageDAO.getImageDAOInstance().getImageURLById(rs.getInt("img_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playlist;

	}

	public void addToPlaylist(int track_id, int playlist_id) {
		
		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_INTO_PLAYLISTS_WITH_TRACKS);
			ps.setInt(1, playlist_id);
			ps.setInt(2, track_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
