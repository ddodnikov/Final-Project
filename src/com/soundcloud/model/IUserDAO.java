package com.soundcloud.model;

import java.util.List;

public interface IUserDAO {
	
	public String getInitialDisplayName(String email);
	
	public void addUser(String email, String password);
	
	public User getUserById(int id);
	
	public boolean isEmailUsed(String email);
	
	public boolean isExistingUser(String email, String password);
	
	public String getImageById(int user_id);
	
	public String getHeaderImgUriByUserId(int id);
	
	public void addImage(String imgUri);
	
	public int getImageByUri(String imgUri);
	
	public void updateProfilePic(int profilePicId, int userId);
	
	public void updateHeaderPic(int headerImageId, int userId);
	
	public String getCurrentProfilePicUri(int userId);
	
	public int getCurrentUserId(String email);
	
	public List<Track> getUserLikes(int userId);
	
	public User selectUserByTrackId(int track_id);
	
	public void updateUserInfo(int userId, String displayName, String firstName, String lastName, 
			String city, String country, String biography);

}
