package com.soundcloud.model;

public interface IUserDAO {
	
	public void addUser(String email, String password);
	
	public void deleteUser();
	
	public void getUser();
	
	public boolean isEmailUsed(String email);
	
	public boolean isExistingUser(String email, String password);

}
