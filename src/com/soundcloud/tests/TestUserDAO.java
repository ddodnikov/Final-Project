package com.soundcloud.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.soundcloud.model.User;
import com.soundcloud.model.UserDAO;

public class TestUserDAO {

	@Test
	public void testGetInitialDisplayName() {
		
		String email = "mitko@abv.bg";
		String name = new UserDAO().getInitialDisplayName(email);
		assertTrue(email.startsWith(name));
		
	}
	
	@Test
	public void testGetUserById() {
		
		User user = new UserDAO().getUserById(7);
		assertNotNull(user);
		
	}
	
	@Test
	public void testIsEmailUsed() {
		
		assertTrue(new UserDAO().isEmailUsed("mimi@abv.bg"));
		assertFalse(new UserDAO().isEmailUsed("mimidf@abv.bg"));
		
	}
	
	@Test
	public void testIsExistingUser() {
		
		assertTrue(new UserDAO().isExistingUser("mimi@abv.bg", "mimimimi"));
		assertFalse(new UserDAO().isExistingUser("mimi@abv.bg", "mimimimi9"));
		
	}
	
	@Test
	public void testGetImageById() {
		
		String url = new UserDAO().getImageById(7);
		assertNotNull(url);
		assertNotEquals("", url);
		
	}
	
	@Test
	public void testGetHeaderImgUriByUserId() {
		
		String url = new UserDAO().getHeaderImgUriByUserId(7);
		assertNotNull(url);
		assertNotEquals("", url);
		
	}

}
