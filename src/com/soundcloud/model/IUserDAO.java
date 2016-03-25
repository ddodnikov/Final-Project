package com.soundcloud.model;

public interface IUserDAO {
	
	public void addUser();
	
	public void deleteUser();
	
	public void getUser();
	
	public boolean isEmailUsed(String email);

}
