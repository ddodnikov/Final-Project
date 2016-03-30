package com.soundcloud.model;

import java.util.List;

public interface ITrackDAO {

	public void addTrack(String title, int ganre_id, String description, String uri, int userId);

	public void deleteTrack();
	
	public void getTrack();
	
	public List<Track> getAllTracks();
	
	public void addImage(String uri);

	int getImageByUri(String uri);
	
	void updateTrackImage(int imgid, String title);
	
	List<Track> getUserTracks(int userId);
	
	List<Track> searchTracksTitleTagsAndGenreByWord(String word);
}
