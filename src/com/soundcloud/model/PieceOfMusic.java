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

}
