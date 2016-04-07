package com.soundcloud.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/NextPreviousSearchPage")
public class NextPreviousSearchPage extends HttpServlet {
	
	private static final String SPLIT_REGEX = "[\\W]+";
	private static final int SONGS_SHOWN = 5;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Track> results = null;

		if (request.getParameter("nextSearchTracks") != null) {

			results = TrackDAO.getTrackDAOInstance().searchTracksTitleTagsAndGenreByWord(
					new TreeSet<String>(Arrays.asList(((String)request.getSession().getAttribute("search")).split(SPLIT_REGEX))),
					(int) request.getSession().getAttribute("songsShown") + SONGS_SHOWN);

			if (!results.isEmpty()) {
				
				if (request.getSession().getAttribute("currentUser") != null) {
					for (Track track : results)
						track.setIsLikedByUser(TrackDAO.getTrackDAOInstance().isTrackLikedByUser(track.getId(),
								((User) request.getSession().getAttribute("currentUser")).getId()));
				}
				
				request.getSession().setAttribute("results", results);
				request.getSession().setAttribute("songsShown",
						(int) request.getSession().getAttribute("songsShown") + SONGS_SHOWN);
			}

		} else {

			if (request.getParameter("previousSearchTracks") != null) {

				if ((int) request.getSession().getAttribute("songsShown") >= SONGS_SHOWN) {
					results = TrackDAO.getTrackDAOInstance().searchTracksTitleTagsAndGenreByWord(
							new TreeSet<String>(Arrays.asList(((String)request.getSession().getAttribute("search")).split(SPLIT_REGEX))),
							(int) request.getSession().getAttribute("songsShown") - SONGS_SHOWN);

					request.getSession().setAttribute("results", results);
					request.getSession().setAttribute("songsShown",
							(int) request.getSession().getAttribute("songsShown") - SONGS_SHOWN);
				}
			}
		}

		request.getRequestDispatcher("/searchResults.jsp").forward(request, response);

	}

}
