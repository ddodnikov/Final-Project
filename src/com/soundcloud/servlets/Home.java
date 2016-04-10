package com.soundcloud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Playlist;
import com.soundcloud.model.PlaylistDAO;
import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession(false) == null) {
			response.sendRedirect("./");
		} else {

			int userId = ((User) request.getSession().getAttribute("currentUser")).getId();

			List<Track> tracksToDisplay = TrackDAO.getTrackDAOInstance().getUserTracks(userId, 0);

			for (int i = 0; i < tracksToDisplay.size(); i++) {
				tracksToDisplay.get(i).setIsLikedByUser(
						TrackDAO.getTrackDAOInstance().isTrackLikedByUser(tracksToDisplay.get(i).getId(), userId));
			}

			request.getSession().setAttribute("tracksToDisplay", tracksToDisplay);
			request.getSession().setAttribute("tracksShown", 0);

			List<Track> likedTracksToDisplay = TrackDAO.getTrackDAOInstance().getUserLikedTracks(userId, 0);

			for (int i = 0; i < likedTracksToDisplay.size(); i++)
				likedTracksToDisplay.get(i).setIsLikedByUser(true);

			request.getSession().setAttribute("likedTracksToDisplay", likedTracksToDisplay);
			request.getSession().setAttribute("likedTracksShown", 0);

			List<Playlist> playlistsToDisplay = PlaylistDAO.getPlaylistDAOInstance().getUserPlaylists(userId, 0);

			request.getSession().setAttribute("playlistsToDisplay", playlistsToDisplay);
			request.getSession().setAttribute("playlistsShown", 0);

			request.getSession().setAttribute("activeTab", "alltracks");

			RequestDispatcher rd = request.getRequestDispatcher("./home.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
