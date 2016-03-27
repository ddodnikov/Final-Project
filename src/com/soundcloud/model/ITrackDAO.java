package com.soundcloud.model;

public interface ITrackDAO {

	public void addTrack(String title, String ganre, String description, String uri, int userId);

	public void deleteTrack();
	
	public void getTrack();

}
