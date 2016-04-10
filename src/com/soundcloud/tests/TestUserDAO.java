package com.soundcloud.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.soundcloud.model.User;
import com.soundcloud.model.UserDAO;

public class TestUserDAO {

	@Test
	public void testGetInitialDisplayName() {
		
		String email = "mitko@abv.bg";
		String name = UserDAO.getUserDAOInstance().getInitialDisplayName(email);
		assertTrue(email.startsWith(name));
		
	}
	
	@Test
	public void testGetUserById() {
		
		User user = UserDAO.getUserDAOInstance().getUserById(7);
		assertNotNull(user);
		
	}
	
	@Test
	public void testIsEmailUsed() {
		
		assertTrue(UserDAO.getUserDAOInstance().isEmailUsed("mimi@abv.bg"));
		assertFalse(UserDAO.getUserDAOInstance().isEmailUsed("mimidf@abv.bg"));
		
	}
	
	@Test
	public void testIsExistingUser() {
		
		assertTrue(UserDAO.getUserDAOInstance().isExistingUser("mimi@abv.bg", "mimimimi"));
		assertFalse(UserDAO.getUserDAOInstance().isExistingUser("mimi@abv.bg", "mimimimi9"));
		
	}
	
	@Test
	public void testGetImageById() {
		
		String url = UserDAO.getUserDAOInstance().getImageById(7);
		assertNotNull(url);
		assertNotEquals("", url);
		
	}
	
	@Test
	public void testGetHeaderImgUriByUserId() {
		
		String url = UserDAO.getUserDAOInstance().getHeaderImgUriByUserId(7);
		assertNotNull(url);
		assertNotEquals("", url);
		
	}

}
