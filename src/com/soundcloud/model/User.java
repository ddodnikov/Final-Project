package com.soundcloud.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String emailAddress;
	private String password;
	
	private String displayName;
	private String firstName;
	private String lastName;
	private String city;
	private String country;
	private StringBuilder biography;
	
	private String userImageURI;
	private String headerImageURI;
	private String userProfileURL;
	
	private List<Track> tracks = new ArrayList<Track>();
	private List<Playlist> playlists = new ArrayList<Playlist>();
	private List<User> followers = new ArrayList<User>();
	private List<User> following = new ArrayList<User>();

}
