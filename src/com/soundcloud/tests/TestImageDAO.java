package com.soundcloud.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.soundcloud.model.ImageDAO;

public class TestImageDAO {

	@Test
	public void testGetImageURLById() {
		
		String url = ImageDAO.getImageDAOInstance().getImageURLById(1);
		assertNotNull(url);
		
	}
	
	@Test
	public void testGetImageIdByURI() {
		
		int id = ImageDAO.getImageDAOInstance().getImagIdByUri(ImageDAO.getImageDAOInstance().getImageURLById(1));
		assertEquals(1, id);
		
	}

}
