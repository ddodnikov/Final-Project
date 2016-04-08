package com.soundcloud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Playlist;
import com.soundcloud.model.PlaylistDAO;
import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;

@WebServlet("/playlists")
public class Playlists extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String stringPlaylistId = request.getParameter("showPlaylistId");
		int playlistId = Integer.parseInt(stringPlaylistId);
		
		Playlist playlist = PlaylistDAO.getPlaylistDAOInstance().getPlaylistById(playlistId);
		
		List<Track> tracks = TrackDAO.getTrackDAOInstance().getPlaylistTracks(playlistId);
		
		request.getSession().setAttribute("showPlaylist", playlist);
		request.getSession().setAttribute("playlistTracks", tracks);
		
		request.getRequestDispatcher("./showPlaylist.jsp").forward(request, response);
	}

}
