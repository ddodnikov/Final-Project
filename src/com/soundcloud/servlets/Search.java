package com.soundcloud.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;

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
		List<String> searchArgs = Arrays.asList(request.getParameter("search").split(SPLIT_REGEX));
		List<Track> results = new ArrayList<Track>();
		for (String word : searchArgs) {
			List<Track> resultTracks = new TrackDAO().searchTracksTitleTagsAndGenreByWord(word);
			results.addAll(resultTracks);
		}

		if (results.size() == 0) {
			request.setAttribute("noResultsMessage", NO_RESULTS_MESSAGE);
		} else {
			request.setAttribute("results", results);
		}
		request.getRequestDispatcher("./searchResults.jsp").forward(request, response);
	}
}