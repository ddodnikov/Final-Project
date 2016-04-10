package com.soundcloud.model;

import java.util.List;

public interface IPlaylistDAO {
	
	public void addPlaylist(int img_id, String title, int userId);
	
	public List<Playlist> getUserPlaylists(int userId, int offset);
	
	public void addToPlaylist(int track_id, int playlist_id);
	
	public Playlist getPlaylistById(int playlistId);
	
	public int getPlaylistImageID(int playlistId);

}
