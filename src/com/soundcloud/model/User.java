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
	
	public String getEmailAddress() {
		return emailAddress;
	}
	private void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	private void setPassword(String password) {
		this.password = password;
	}
	public String getDisplayName() {
		return displayName;
	}
	private void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getFirstName() {
		return firstName;
	}
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCity() {
		return city;
	}
	private void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	private void setCountry(String country) {
		this.country = country;
	}
	public StringBuilder getBiography() {
		return biography;
	}
	private void setBiography(StringBuilder biography) {
		this.biography = biography;
	}
	public String getUserImageURI() {
		return userImageURI;
	}
	private void setUserImageURI(String userImageURI) {
		this.userImageURI = userImageURI;
	}
	public String getHeaderImageURI() {
		return headerImageURI;
	}
	private void setHeaderImageURI(String headerImageURI) {
		this.headerImageURI = headerImageURI;
	}
	public String getUserProfileURL() {
		return userProfileURL;
	}
	private void setUserProfileURL(String userProfileURL) {
		this.userProfileURL = userProfileURL;
	}
	

}
