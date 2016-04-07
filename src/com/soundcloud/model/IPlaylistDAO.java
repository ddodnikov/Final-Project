package com.soundcloud.model;

import java.util.List;

public interface IPlaylistDAO {
	
	public void addPlaylist(int img_id, String title, int userId);

	public void deletePlaylist();
	
	public List<Playlist> getUserPlaylists(int userId, int offset);

}
