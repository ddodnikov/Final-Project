package com.soundcloud.model;

import java.util.List;

public class Track extends PieceOfMusic{
	

	private String trackURL;
	
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
	
	

}
