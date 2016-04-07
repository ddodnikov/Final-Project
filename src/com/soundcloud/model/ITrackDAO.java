package com.soundcloud.model;

import java.util.List;
import java.util.Set;

public interface ITrackDAO {

	public void addTrack(String title, int ganre_id, String description, String uri, int userId, Set<String> tags);

	public void deleteTrack();
	
	public void getTrack();
	
	public List<Track> getAllTracks();
	
	void updateTrackImage(int imgid, String title);
	
	List<Track> getUserTracks(int userId, int offset);
	
	List<Track> searchTracksTitleTagsAndGenreByWord(Set<String> words, int offset);
	
	public boolean isTrackLikedByUser(int track_id, int user_id);
	
	public void likeTrack(int track_id, int user_id);
	
	public void unlikeTrack(int track_id, int user_id);

	Track getTrackById(int trackId);
	
	String getTrackImageUri(int trackId);
	
	void addTag(String tag);
}
