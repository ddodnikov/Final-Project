package com.soundcloud.model;

import java.io.Serializable;

import com.soundcloud.exceptions.SoundCloudInvalidArgumentException;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String emailAddress;
	private String password;
	private String displayName;
	private String firstName;
	private String lastName;
	private String city;
	private String country;
	private String biography;
	private int userImageID;
	private int headerImageID;

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

	public int getUserImageID() {
		return userImageID;
	}

	public int getHeaderImageID() {
		return headerImageID;
	}
	
	public void setHeaderImageID(int url) {
		this.headerImageID = url;
	}

	public void setUserImageID(int imageURL) {
		this.userImageID = imageURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
