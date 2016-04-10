package com.soundcloud.model;

import java.util.List;

public interface IGenreDAO {

	public int getIdByName(String name);
	
	public String getNameById(int genreId);
	
	public List<String> getAllGenreNames();
}
