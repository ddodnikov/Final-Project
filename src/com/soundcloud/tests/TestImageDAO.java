package com.soundcloud.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.soundcloud.model.ImageDAO;

public class TestImageDAO {

	@Test
	public void testGetImageURLById() {
		
		String url = new ImageDAO().getImageURLById(1);
		assertNotNull(url);
		
	}
	
	@Test
	public void testGetImageIdByURI() {
		
		int id = new ImageDAO().getImagIdByUri(new ImageDAO().getImageURLById(1));
		assertEquals(1, id);
		
	}

}
