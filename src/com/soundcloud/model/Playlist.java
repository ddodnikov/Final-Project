package com.soundcloud.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends PieceOfMusic implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Track> playlistTracks = new ArrayList<Track>();
	
	private User user = new User();

	private int numberOfTracks;

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public void setNumberOfTracks(int numberOfTracks) {
		this.numberOfTracks = numberOfTracks;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
