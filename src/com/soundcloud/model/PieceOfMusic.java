package com.soundcloud.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class PieceOfMusic {
	
	private String title;
	private String ganre;
	private List<String> tags = new ArrayList<String>();
	private String description;
	private Date dateAdded;
	
	private int numberOfLikes;
	private String imageURI;
	
	private User user;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title != null && !title.isEmpty())
			this.title = title;
	}

	public String getGanre() {
		return ganre;
	}

	public void setGanre(String ganre) {
		if (ganre != null && !ganre.isEmpty())
			this.ganre = ganre;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public String getImageURI() {
		return imageURI;
	}

	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
