package com.soundcloud.servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;

@WebServlet("/NextPreviousSearchPage")
public class NextPreviousSearchPage extends HttpServlet {
	private static final int SONGS_SHOWN = 5;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		Set<Track> results = null;
				
		if (request.getParameter("next") != null) {
			
			results = new TrackDAO().searchTracksTitleTagsAndGenreByWord((String)request.getSession().getAttribute("search"),
					(int)request.getSession().getAttribute("songsShown"));
			
			if(!results.isEmpty()) {
				request.getSession().setAttribute("results", results);
				request.getSession().setAttribute("songsShown", (int)request.getSession().getAttribute("songsShown") + SONGS_SHOWN);
			}
			
		}else {
			
			if(request.getParameter("previous") != null) {
				
				if((int) request.getSession().getAttribute("songsShown") > SONGS_SHOWN) {
					results = new TrackDAO().searchTracksTitleTagsAndGenreByWord((String)request.getSession().getAttribute("search"),
							(int)request.getSession().getAttribute("songsShown") - SONGS_SHOWN * 2);
				
					request.getSession().setAttribute("results", results);
					request.getSession().setAttribute("songsShown", (int)request.getSession().getAttribute("songsShown") - SONGS_SHOWN * 2);
				}
			}
		}
		
		request.getRequestDispatcher("/searchResults.jsp").forward(request, response);

	}

}
