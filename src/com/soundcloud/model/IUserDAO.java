package com.soundcloud.model;

public interface IUserDAO {
	
	public void addUser(String email, String password);
	
	public void deleteUser();
	
	public User getUserById(int id);
	
	public boolean isEmailUsed(String email);
	
	public boolean isExistingUser(String email, String password);
	
	String getHeaderImgUriByUserId(int id);

}
