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
import com.soundcloud.model.User;

@WebServlet("/NextPreviousTrackPage")
public class NextPreviousTrackPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		List<Track> tracksToDsiplay = null;
				
		if (request.getParameter("next") != null) {
			
			tracksToDsiplay = new TrackDAO().getUserTracks(((User) request.getSession().getAttribute("currentUser")).getId(),
					(int) request.getSession().getAttribute("tracksShown"));
			
			for(int i = 0; i < tracksToDsiplay.size(); i++) {
				tracksToDsiplay.get(i).setIsLikedByUser(new TrackDAO().isTrackLikedByUser(
						tracksToDsiplay.get(i).getId(), ((User) request.getSession().getAttribute("currentUser")).getId()));
			}
			
			if(!tracksToDsiplay.isEmpty()) {
				request.getSession().setAttribute("tracksToDisplay", tracksToDsiplay);
				request.getSession().setAttribute("tracksShown", (int)request.getSession().getAttribute("tracksShown") + 10);
			}
			
		}else {
			
			if(request.getParameter("previous") != null) {
				
				if((int) request.getSession().getAttribute("tracksShown") > 10) {
					tracksToDsiplay = new TrackDAO().getUserTracks(((User) request.getSession().getAttribute("currentUser")).getId(),
						(int) request.getSession().getAttribute("tracksShown") - 20);
					
					request.getSession().setAttribute("tracksShown", (int)request.getSession().getAttribute("tracksShown") - 20);
					request.getSession().setAttribute("tracksToDisplay", tracksToDsiplay);
				}
				
			}
		}
		
		request.getRequestDispatcher("/Home").forward(request, response);

	}

}
