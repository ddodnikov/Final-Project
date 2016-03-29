package com.soundcloud.model;

public interface IGenreDAO {

	public int getIdByName(String name);
	
	public String getNameById(int genreId);
}
