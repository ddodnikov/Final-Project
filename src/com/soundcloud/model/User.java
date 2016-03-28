package com.soundcloud.model;

import java.util.ArrayList;
import java.util.List;

import com.soundcloud.exceptions.SoundCloudInvalidArgumentException;

public class User {

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

	public String getEmailAddress() {
		return emailAddress;
	}

	private void setEmailAddress(String emailAddress) {
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

	private void setDisplayName(String displayName) {
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

	private void setFirstName(String firstName) {
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

	private void setLastName(String lastName) {
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

	private void setCity(String city) {
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

	private void setCountry(String country) {
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

	private void setBiography(String biography) {
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
