package com.soundcloud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;

@WebServlet("/NextPreviousSearchPage")
public class NextPreviousSearchPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		List<Track> results = null;
				
		if (request.getParameter("next") != null) {
			
			results = new TrackDAO().searchTracksTitleTagsAndGenreByWord((String)request.getSession().getAttribute("search"),
					(int)request.getSession().getAttribute("songsShown"));
			
			if(!results.isEmpty()) {
				request.getSession().setAttribute("results", results);
				request.getSession().setAttribute("songsShown", (int)request.getSession().getAttribute("songsShown") + 10);
			}
			
		}else {
			
			if(request.getParameter("previous") != null) {
				
				if((int) request.getSession().getAttribute("songsShown") > 10) {
					results = new TrackDAO().searchTracksTitleTagsAndGenreByWord((String)request.getSession().getAttribute("search"),
							(int)request.getSession().getAttribute("songsShown") - 20);
				
					request.getSession().setAttribute("results", results);
					request.getSession().setAttribute("songsShown", (int)request.getSession().getAttribute("songsShown") - 20);
				}
			}
		}
		
		request.getRequestDispatcher("/searchResults.jsp").forward(request, response);

	}

}
