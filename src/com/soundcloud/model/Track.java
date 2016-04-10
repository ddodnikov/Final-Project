package com.soundcloud.model;

import java.io.Serializable;
import java.util.List;

public class Track extends PieceOfMusic implements Serializable, Comparable<Track> {

	private static final long serialVersionUID = 1L;

	private boolean isLikedByUser;
	private boolean isUnlikedByUser;
	
	public void setLikedByUser(boolean isLikedByUser) {
		this.isLikedByUser = isLikedByUser;
	}

	public boolean isUnlikedByUser() {
		return isUnlikedByUser;
	}

	public void setUnlikedByUser(boolean isUnlikedByUser) {
		this.isUnlikedByUser = isUnlikedByUser;
	}

	private List<String> comments;
	
	private int numberOfPlays;
	
	private String trackURL;

	public int getNumberOfPlays() {
		return numberOfPlays;
	}

	public void setNumberOfPlays(int numberOfPlays) {
		this.numberOfPlays = numberOfPlays;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	@Override
	public int compareTo(Track track) {
		if (this.getId() == track.getId())
			return 0;
		return 1;
	}

	public boolean getIsLikedByUser() {
		return isLikedByUser;
	}

	public void setIsLikedByUser(boolean isLikedByUser) {
		this.isLikedByUser = isLikedByUser;
	}

	public String getTrackURL() {
		return trackURL;
	}

	public void setTrackURL(String trackURL) {
		this.trackURL = trackURL;
	}
}
