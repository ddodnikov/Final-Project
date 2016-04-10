package com.soundcloud.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/Search")
public class Search extends HttpServlet {

	private static final String NO_RESULTS_MESSAGE = "Sorry, there are no tracks or artists with such names!";
	private static final String SPLIT_REGEX = "[\\W]+";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("search") == null || request.getParameter("search").isEmpty()) {
			request.setAttribute("noResultsMessage", NO_RESULTS_MESSAGE);
		}

		Set<String> searchArgs = new TreeSet<String>(Arrays.asList(request.getParameter("search").split(SPLIT_REGEX)));
		
		List<Track> results = new ArrayList<Track>();
		
		results = TrackDAO.getTrackDAOInstance().searchTracksTitleTagsAndGenreByWord(searchArgs, 0);

		if (results.size() == 0) {
			request.setAttribute("noResultsMessage", NO_RESULTS_MESSAGE);
		} else {
			request.getSession().setAttribute("search", request.getParameter("search"));
			request.getSession().setAttribute("songsShown", 0);
			request.getSession().setAttribute("results", results);
			
			if(request.getSession().getAttribute("currentUser") != null) {
				for(Track track : results)
					track.setIsLikedByUser(TrackDAO.getTrackDAOInstance().isTrackLikedByUser(
							track.getId(), ((User) request.getSession().getAttribute("currentUser")).getId()));
			}
		}
		request.getRequestDispatcher("./searchResults.jsp").forward(request, response);
	}
}