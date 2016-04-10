package com.soundcloud.model;

import java.util.List;

public interface IPlaylistDAO {
	
	public void addPlaylist(int img_id, String title, int userId);
	
	public List<Playlist> getUserPlaylists(int userId, int offset);

	void addTrackToPlaylist(int trackId, int playlistId);
	
	void incrementPlaylistTracksCount(int playlistId);

	boolean isTrackInPlaylist(int playlistId, int trackId);
	
	public Playlist getPlaylistById(int playlistId);
	
	public int getPlaylistImageID(int playlistId);

}
