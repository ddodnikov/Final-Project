package com.soundcloud.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.PlaylistDAO;

/**
 * Servlet implementation class IsTrackInPlaylist
 */
@WebServlet("/IsTrackInPlaylist")
public class IsTrackInPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int playlistId = Integer.parseInt(request.getParameter("playlistId"));
		int trackId = Integer.parseInt(request.getParameter("trackId"));
		boolean trackExists = PlaylistDAO.getPlaylistDAOInstance().isTrackInPlaylist(playlistId, trackId);
		if (trackExists) {
			String trackExistsResult = Boolean.toString(trackExists);
			request.setAttribute("trackExists", trackExistsResult);
			response.getWriter().write("trackExists");
		}
	}
}