package com.soundcloud.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO implements IUserDAO {
	
	private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?;";
	private static final String GET_USER_ID_BY_EMAIL_QUERY = "SELECT user_id FROM users where email = ?;";
	private static final String GET_PROFILE_IMG_URI_QUERY = "SELECT i.img_uri FROM images i INNER JOIN users u ON i.img_id = u.user_img_id WHERE user_id = ?;";
	private static final String GET_HEADER_IMG_URI_QUERY = "SELECT i.img_uri FROM images i INNER JOIN users u ON i.img_id = u.header_img_id WHERE user_id = ?;";
	private static final String INSERT_USER = "INSERT INTO users (email, password,display_name) VALUES(?,?,?);";
	private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
	private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
	private static final String SELECT_IMAGE_BY_USER_ID = "SELECT user_img_id FROM users WHERE user_id = ?;";
	private static final String SELECT_USER_LIKES = "SELECT * FROM tracks t INNER JOIN users_likes ul ON "
			+ "t.track_id = ul.track_id INNER JOIN users u ON ul.user_id = u.user_id WHERE u.user_id = ?;";
	private static final String SELECT_USER_BY_TRACK_ID = "SELECT * FROM users WHERE "
			+ "user_id = (SELECT user_id FROM tracks WHERE track_id =?);";
	
	public String getInitialDisplayName(String email) {
		
		StringBuilder name = new StringBuilder();
		
		int index = 0;
		while(email.charAt(index) != '@') {
			name.append(email.charAt(index));
			index++;
		}

		return name.toString();
	}
	
	@Override
	public void addUser(String email, String password) {

		PreparedStatement addUser = null;
		
		try {
			addUser = getCon().prepareStatement(INSERT_USER);
			addUser.setString(1, email);
			addUser.setString(2, password);
			addUser.setString(3, getInitialDisplayName(email));
			
			addUser.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public User getUserById(int id) {
		User result = null;
		PreparedStatement ps;
		try {
			ps = getCon().prepareStatement("SELECT * FROM users WHERE user_id = ?;");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String email = rs.getString(2);
				String displayName = rs.getString(4);
				String firstName = rs.getString(5);
				String lastName = rs.getString(6);
				String city = rs.getString(7);
				String country = rs.getString(8);
				String biography = rs.getString(9);
				result = new User(email, displayName, firstName, lastName, city, country, biography);
				result.setId(rs.getInt(1));
				result.setUserImageURI(new ImageDAO().getImageURLById(rs.getInt(10)));
				result.setHeaderImageURI(new ImageDAO().getImageURLById(rs.getInt(11)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return result;
		
	}

	@Override
	public boolean isEmailUsed(String email){

		PreparedStatement hasAUser = null;
		ResultSet result = null;
		
		try {
			hasAUser = getCon().prepareStatement(SELECT_USER_BY_EMAIL);
			hasAUser.setString(1, email);
			hasAUser.execute();
			result = hasAUser.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(!result.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public boolean isExistingUser(String email, String password) {
		
		PreparedStatement hasAUser = null;
		ResultSet result = null;
		
		try {
			hasAUser = getCon().prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD);
			hasAUser.setString(1, email);
			hasAUser.setString(2, password);
			hasAUser.execute();
			result = hasAUser.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String getImageById(int user_id) {

		PreparedStatement selectImage = null;
		
		ResultSet result = null;
		
		String imgURL = "";
		try {
			selectImage = getCon().prepareStatement(SELECT_IMAGE_BY_USER_ID);
			
			selectImage.setInt(1, user_id);
			
			selectImage.execute();
			
			result = selectImage.getResultSet();
			result.next();
			
			int imgId = result.getInt(1);
			
			imgURL = new ImageDAO().getImageURLById(imgId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return imgURL;
	}

	@Override
	public String getHeaderImgUriByUserId(int id) {
		String headerImgUri = "";
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_HEADER_IMG_URI_QUERY);
			ps.setInt(1, id);
			ResultSet headerImgUriResult = ps.executeQuery();
			if (headerImgUriResult.next()) {
				headerImgUri = headerImgUriResult.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return headerImgUri;
	}

	public void addImage(String imgUri) {
		try {
			PreparedStatement ps = getCon().prepareStatement("INSERT INTO images (img_id, img_uri) VALUES(null, ?);");
			ps.setString(1, imgUri);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int getImageByUri(String imgUri) {
		int imageId = 0;
		try {
			PreparedStatement ps = getCon().prepareStatement("SELECT * FROM images WHERE img_uri = ?;");
			ps.setString(1, imgUri);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				imageId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (imageId == 0) {
			return 1; // default img uri??
		}
		return imageId;
	}

	public void updateProfilePic(int profilePicId, int userId) {
		try {
			PreparedStatement ps = getCon().prepareStatement("UPDATE users SET user_img_id = ? WHERE user_id = ?;");
			ps.setInt(1, profilePicId);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void updateHeaderPic(int headerImageId, int userId) {
		try {
			PreparedStatement ps = getCon().prepareStatement("UPDATE users SET header_img_id = ? WHERE user_id = ?;");
			ps.setInt(1, headerImageId);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getCurrentProfilePicUri(int userId) {
		String currentProfilePicUri = null;
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_PROFILE_IMG_URI_QUERY);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				currentProfilePicUri = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentProfilePicUri;
	}
	
	public int getCurrentUserId(String email) {
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_USER_ID_BY_EMAIL_QUERY);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			int userId = 0;
			if (rs.next()) {
				userId = rs.getInt(1);
			}
			return userId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Track> getUserLikes(int userId) {
		
		List<Track> likedTracks = new ArrayList<Track>();
		
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_USER_LIKES);
			ps.setInt(1, userId);
			ResultSet tracks = ps.executeQuery();
			
			while (tracks.next()) {
				Track track = new Track();
				int genreId = tracks.getInt("genre_id");
				GenreDAO genreDao = new GenreDAO();
				String genreName = genreDao.getNameById(genreId);
				track.setTrackURL(tracks.getString("track_uri"));
				track.setDateAdded(tracks.getTimestamp("date_added"));
				track.setTitle(tracks.getString("title"));
				track.setGanre(genreName);
				track.setDescription(tracks.getString("description"));
				track.setNumberOfLikes(tracks.getInt("likes_count"));
				track.setNumberOfPlays(tracks.getInt("plays_count"));
				likedTracks.add(track);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return likedTracks;
		
	}
	
	public User selectUserByTrackId(int track_id) {
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_USER_BY_TRACK_ID);
			ps.setInt(1, track_id);
			ResultSet rs = ps.executeQuery();
			User user = null;
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("user_id"));
				user.setDisplayName(rs.getString("display_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setLastName(rs.getString("city"));
				user.setLastName(rs.getString("country"));
				user.setLastName(rs.getString("biography"));
				user.setUserImageURI(new ImageDAO().getImageURLById(rs.getInt("user_img_id")));
				user.setHeaderImageURI(new ImageDAO().getImageURLById(rs.getInt("header_img_id")));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
