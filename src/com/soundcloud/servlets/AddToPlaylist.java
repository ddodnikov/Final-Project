package com.soundcloud.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.PlaylistDAO;

@WebServlet("/addToPlaylist")
public class AddToPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int track_id = Integer.parseInt(request.getParameter("trackId"));
		System.out.println(track_id);
		
		int playlist_id = Integer.parseInt(request.getParameter("playlistId"));
		System.out.println(playlist_id);
		
		PlaylistDAO.getPlaylistDAOInstance().addToPlaylist(track_id, playlist_id);
		
	}

}
