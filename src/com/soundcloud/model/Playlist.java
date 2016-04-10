package com.soundcloud.model;

import java.io.Serializable;

public class Playlist extends PieceOfMusic implements Serializable{

	private static final long serialVersionUID = 1L;

	private int numberOfTracks;

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public void setNumberOfTracks(int numberOfTracks) {
		this.numberOfTracks = numberOfTracks;
	}

}
