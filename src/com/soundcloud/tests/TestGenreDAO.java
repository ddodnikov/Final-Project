package com.soundcloud.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.soundcloud.model.GenreDAO;

public class TestGenreDAO {

	@Test
	public void testGetNameById() {
		
		String name = GenreDAO.getGenreDAOInstance().getNameById(1);
		assertNotNull(name);
		assertNotEquals("", name);
		
	}
	
	@Test
	public void testGetIdByName() {
		
		int id = GenreDAO.getGenreDAOInstance().getIdByName(GenreDAO.getGenreDAOInstance().getNameById(1));
		assertEquals(1, id);
		
	}
	
	@Test
	public void testGetAllGenreNames() {
		
		List<String> allGenres = GenreDAO.getGenreDAOInstance().getAllGenreNames();
		assertNotNull(allGenres);
		assertNotEquals(0, allGenres.size());
		
	}

}
