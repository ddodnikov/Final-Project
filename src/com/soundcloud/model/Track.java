package com.soundcloud.model;

import java.io.Serializable;
import java.util.List;

public class Track extends PieceOfMusic implements Serializable {

	private static final long serialVersionUID = 1L;
	private String trackURL;
	private boolean isLikedByUser;
	
	private List<String> comments;
	
	private int numberOfPlays;

	public String getTrackURL() {
		return trackURL;
	}

	public void setTrackURL(String trackURL) {
		this.trackURL = trackURL;
	}

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

	public boolean getIsLikedByUser() {
		return isLikedByUser;
	}

	public void setIsLikedByUser(boolean isLikedByUser) {
		this.isLikedByUser = isLikedByUser;
	}
}
