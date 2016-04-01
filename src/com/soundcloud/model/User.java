package com.soundcloud.model;

import java.util.ArrayList;
import java.util.List;

import com.soundcloud.exceptions.SoundCloudInvalidArgumentException;

public class User {

	private int id;
	private String emailAddress;
	private String password;
	private String displayName;
	private String firstName;
	private String lastName;
	private String city;
	private String country;
	private String biography;
	private String userImageURI;
	private String headerImageURI;
	private String userProfileURL;
	private List<Track> tracks = new ArrayList<Track>();
	private List<Playlist> playlists = new ArrayList<Playlist>();
	private List<User> followers = new ArrayList<User>();
	private List<User> following = new ArrayList<User>();

	public User(String email, String displayName, String firstName, String lastName, String city, String country,
			String biography) {
		// validations are not needed at the moment because some fields can be null!
		setEmailAddress(email);
		setDisplayName(displayName);
		setFirstName(firstName);
		setLastName(lastName);
		setCity(city);
		setCountry(country);
		setBiography(biography);
	}
	
	public User(){
		
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		if (emailAddress == null || emailAddress.isEmpty()) {
			try {
				throw new SoundCloudInvalidArgumentException("User email cannot be empty!");
			} catch (SoundCloudInvalidArgumentException e) {
				e.printStackTrace();
			}
		}
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()) {
			try {
				throw new SoundCloudInvalidArgumentException("Display name cannot be empty!");
			} catch (SoundCloudInvalidArgumentException e) {
				e.printStackTrace();
			}
		}
		this.displayName = displayName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
//		if (firstName == null || firstName.isEmpty()) {
//			try {
//				throw new SoundCloudInvalidArgumentException("First name cannot be empty!");
//			} catch (SoundCloudInvalidArgumentException e) {
//				e.printStackTrace();
//			}
//		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
//		if (lastName == null || lastName.isEmpty()) {
//			try {
//				throw new SoundCloudInvalidArgumentException("Last name cannot be empty!");
//			} catch (SoundCloudInvalidArgumentException e) {
//				e.printStackTrace();
//			}
//		}
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
//		if (city == null || city.isEmpty()) {
//			try {
//				throw new SoundCloudInvalidArgumentException("City cannot be empty!");
//			} catch (SoundCloudInvalidArgumentException e) {
//				e.printStackTrace();
//			}
//		}
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
//		if (country == null || country.isEmpty()) {
//			try {
//				throw new SoundCloudInvalidArgumentException("Country cannot be empty!");
//			} catch (SoundCloudInvalidArgumentException e) {
//				e.printStackTrace();
//			}
//		}
		this.country = country;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
//		if (biography == null || biography.isEmpty()) {
//			try {
//				throw new SoundCloudInvalidArgumentException("Biography cannot be empty!");
//			} catch (SoundCloudInvalidArgumentException e) {
//				e.printStackTrace();
//			}
//		}
		this.biography = biography;
	}

	public String getUserImageURI() {
		return userImageURI;
	}

	public String getHeaderImageURI() {
		return headerImageURI;
	}
	
	public void setHeaderImageURI(String url) {
		this.headerImageURI = url;
	}

	public String getUserProfileURL() {
		return userProfileURL;
	}

	public void setUserImageURI(String imageURL) {
		this.userImageURI = imageURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
