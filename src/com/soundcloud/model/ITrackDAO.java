package com.soundcloud.model;

import java.util.List;
import java.util.Set;

import com.soundcloud.exceptions.TrackAlreadyLikedException;

public interface ITrackDAO {

	public void addTrack(String title, int genreId, String description, String uri, int userId, Set<String> tags);

	public void deleteTrack();
	
	public void getTrack();
	
	public List<Track> getAllTracks();
	
	void updateTrackImage(int imgid, String title);
	
	List<Track> getUserTracks(int userId, int offset);
	
	List<Track> searchTracksTitleTagsAndGenreByWord(Set<String> words, int offset);
	
	public boolean isTrackLikedByUser(int trackId, int userId);
	
	boolean isTrackUnlikedByUser(int trackId, int userId);
	
	public void likeTrack(int trackId, int userId) throws TrackAlreadyLikedException;
	
	public void unlikeTrack(int trackId, int userId) throws TrackAlreadyLikedException;

	Track getTrackById(int trackId);
	
	String getTrackImageUri(int trackId);
	
	int addTag(String tag);
	
	void addPlayToTrack(int trackId);
	
	void incrementTrackLikesCount(int trackId, int userId);
	
	void decrementTrackLikesCount(int trackId, int userId);
	
	int getTrackLikes(int trackId);
}
